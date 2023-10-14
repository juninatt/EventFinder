package se.pbt.socialalert.integrationtest.controller;

import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.RestAssured;
import jakarta.inject.Inject;
import org.junit.jupiter.api.*;
import se.pbt.socialalert.model.dto.AlertCreationDTO;
import se.pbt.socialalert.model.entity.Alert;
import se.pbt.socialalert.repository.AlertRepository;
import se.pbt.socialalert.testobject.TestObjectCreator;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
@DisplayName("AlertController Integration Tests:")
public class AlertControllerTest {
    @Inject
    private EmbeddedServer server;
    @Inject
    private AlertRepository alertRepository;

    private static String alertTrigger;
    private static final String BASE_URL = "/alerts";


    // Setup before- and after actions

    @BeforeAll
    static void init() {
        alertTrigger = "SocialAlert In Controller Integration Test";
    }

    @AfterEach
    void cleanUp() {
        alertRepository.deleteAll().block();
    }

    // Tests

    @Nested
    @DisplayName("RestAssured Tests:")
    class RestAssuredTest {
        private String alertId;


        @BeforeEach
        public void setUp() {
            Alert alert = TestObjectCreator.alert(alertTrigger);
            alertRepository.save(alert).block();
            alertId = alert.getId();
            RestAssured.baseURI = server.getURI().toString();
        }

        @Test
        @DisplayName("Retrieve all alerts returns status 200 with stored alert when alert exists")
        public void testGetAllAlerts_withMatchingId() {
            List<Alert> alerts =
                    given()
                            .contentType("application/json")
                            .when()
                            .get(BASE_URL)
                            .then()
                            .statusCode(200)
                            .extract()
                            .jsonPath()
                            .getList("", Alert.class);

            assertThat(alerts).isNotEmpty();

            for (Alert alert : alerts) {
                assertThat(alert.getId()).isEqualTo(alertId);
            }
        }

        @Test
        @DisplayName("Retrieve alert by ID returns status 200 with stored alert when alert exists")
        public void testGetAlertById_withMatchingId() {
            given()
                    .contentType("application/json")
                    .when()
                    .get(BASE_URL + "/{id}", alertId)
                    .then()
                    .statusCode(200)
                    .body("id", equalTo(alertId));
        }

        @Test
        @DisplayName("Retrieve alert by ID returns status 404 when no matching ID is found")
        public void testGetAlertById_notMatchingId() {
            given()
                    .contentType("application/json")
                    .when()
                    .get(BASE_URL + "{id}", "nonExistentId")
                    .then()
                    .statusCode(404);
        }

        @Test
        @DisplayName("Delete alert by ID returns status 200 with deletion message when matching ID is found")
        public void  testDeleteAlertById_withMatchingId() {
            given()
                    .when()
                    .delete(BASE_URL + "/{id}", alertId)
                    .then()
                    .statusCode(200)
                    .body(equalTo("Alert deleted successfully"));
        }

        @Test
        @DisplayName("Delete alert by ID returns status 404 when no matching ID is found")
        public void testDeleteAlertById_noMatchingId() {
            given()
                    .contentType("application/json")
                    .when()
                    .delete(BASE_URL + "{id}", "nonExistentId")
                    .then()
                    .statusCode(404);
        }
    }


    // Private helper methods

    private static void assertSameFields(AlertCreationDTO dto, Alert entity) {
        assertEquals(dto.trigger(), entity.getTrigger());
        assertEquals(dto.triggerContext(), entity.getTriggerContext());
        assertEquals(dto.userAccount(), entity.getUserAccount());
        assertEquals(dto.ipAddress(), entity.getIpAddress());
        assertEquals(dto.sourceReference(), entity.getSourceReference());
        assertEquals(dto.geographicLocation(), entity.getGeographicLocation());
    }

    private static void assertSameFields(Alert a1, Alert a2) {
        assertEquals(a1.getTrigger(), a2.getTrigger());
        assertEquals(a1.getTriggerContext(), a2.getTriggerContext());
        assertEquals(a1.getUserAccount(), a2.getUserAccount());
        assertEquals(a1.getIpAddress(), a2.getIpAddress());
        assertEquals(a1.getSourceReference(), a2.getSourceReference());
        assertEquals(a1.getGeographicLocation(), a2.getGeographicLocation());
    }
}

