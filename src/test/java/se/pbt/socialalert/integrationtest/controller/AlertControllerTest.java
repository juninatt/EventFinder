package se.pbt.socialalert.integrationtest.controller;

import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.RestAssured;
import jakarta.inject.Inject;
import org.junit.jupiter.api.*;
import se.pbt.socialalert.model.entity.Alert;
import se.pbt.socialalert.repository.AlertRepository;
import se.pbt.socialalert.testobject.TestObjectCreator;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

@MicronautTest
@DisplayName("SocialAlertController Integration Tests:")
public class AlertControllerTest {
    @Inject
    private EmbeddedServer server;
    @Inject
    private AlertRepository repository;

    private static String alertTrigger;
    private static String baseUrl;



    @BeforeAll
    static void init() {
        alertTrigger = "SocialAlert In Controller Integration Test";
        baseUrl = "/alerts";
    }

    @AfterEach
    void cleanUp() {
        repository.deleteAll().block();
    }

    @Nested
    @DisplayName("RestAssured Tests:")
    class RestAssuredTest {
        private String alertId;


        @BeforeEach
        public void setUp() {
            Alert alert = TestObjectCreator.socialAlert(alertTrigger);
            repository.save(alert).block();
            alertId = alert.getId();
            RestAssured.baseURI = server.getURI().toString();
        }


        @Test
        @DisplayName("Retrieve all alerts returns status 200 with stored alert when alert exists")
        public void testGetAllAlerts_withMatchingId() {
            given()
                    .contentType("application/json")
                    .when()
                    .get(baseUrl)
                    .then()
                    .statusCode(200)
                    .body("id", equalTo(alertId));

        }

        @Test
        @DisplayName("Retrieve alert by ID returns status 200 with stored alert when alert exists")
        public void testGetAlertById_withMatchingId() {
            given()
                    .contentType("application/json")
                    .when()
                    .get(baseUrl + "/{id}", alertId)
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
                    .get(baseUrl + "{id}", "nonExistentId")
                    .then()
                    .statusCode(404);
        }

        @Test
        @DisplayName("Delete alert by ID returns status 200 with deletion message when matching ID is found")
        public void testDeleteAlertById_withMatchingId() {
            given()
                    .when()
                    .delete(baseUrl + "/{id}", alertId)
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
                    .delete(baseUrl + "{id}", "nonExistentId")
                    .then()
                    .statusCode(404);
        }
    }
}

