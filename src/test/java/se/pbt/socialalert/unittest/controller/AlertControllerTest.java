package se.pbt.socialalert.unittest.controller;

import io.micronaut.http.MutableHttpResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import se.pbt.socialalert.controller.AlertController;
import se.pbt.socialalert.model.dto.AlertCreationDTO;
import se.pbt.socialalert.model.entity.Alert;
import se.pbt.socialalert.service.AlertService;
import se.pbt.socialalert.testobject.TestObjectCreator;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@DisplayName("AlertController Unit Tests")
@ExtendWith(MockitoExtension.class)
class AlertControllerTest {

    // Fields
    @Mock
    private AlertService alertService;
    @InjectMocks
    private AlertController alertController;

    private static String testEventName, testDTOName;

    // Setup before- and after action

    @BeforeAll
    static void init() {
        testEventName = "Controller Integration Test Event";
        testDTOName = "Controller Integration Test DTO";
    }

    // Tests

    @Nested
    @DisplayName("Tests for retrieving alerts:")
    class RetrieveAllAlertTest {

        @Test
        @DisplayName("Retrieves list of all alerts with correct field values and a response status 200 OK")
        void returnsAlertsWhenAlertExist_withStatus200Ok() {
            Alert alert = TestObjectCreator.alert(testEventName);

            when(alertService.getAllAlerts()).thenReturn(Flux.just(alert));

            var response = alertController.getAlerts();

            StepVerifier.create(response)
                    .expectNextMatches(httpResponse -> {
                        assertHttpStatusIs(200, httpResponse);
                        httpResponse.body().forEach(index -> assertEquals(alert, index));
                        return true;
                    })
                    .verifyComplete();
        }

        @Test
        @DisplayName("Retrieving all alerts returns empty list with response status 200 OK when no alerts exist")
        void returnsEmptyListAnd200Ok_whenNoAlertsExist() {
            when(alertService.getAllAlerts()).thenReturn(Flux.empty());

            var response = alertController.getAlerts();

            StepVerifier.create(response)
                    .expectNextMatches(httpResponse -> {
                        assertHttpStatusIs(200, httpResponse);
                        List<Alert> alerts = httpResponse.getBody().get();
                        assertThat(alerts).isEmpty();
                        return true;
                    })
                    .verifyComplete();
        }

        @Test
        @DisplayName("Retrieves alert with correct field values and a response status 200 OK")
        void returnsAlertWhenAlertExists_withStatus200Ok() {
            Alert alert = TestObjectCreator.alert(testEventName);

            when(alertService.getAlertById(anyString())).thenReturn(Mono.just(alert));

            var response = alertController.getAlert(alert.getId());

            StepVerifier.create(response)
                    .expectNextMatches(httpResponse -> assertHttpStatusIs(200, httpResponse)
                            && assertFieldsMatch(alert, httpResponse))
                    .verifyComplete();
        }
    }

    @Nested
    @DisplayName("Tests for actions modifying database alerts:")
    class ModifyDatabaseTest {

        @Test
        @DisplayName("Creates alert and returns created alert with response status 201 CREATED")
        void addsAlertToDataBase_andReturnsStatus201Created() {
            AlertCreationDTO alertCreationDTO = TestObjectCreator.alertCreationDTO(testDTOName);
            Alert alert = TestObjectCreator.alert(testEventName);

            when(alertService.createAlert(alertCreationDTO)).thenReturn(Mono.just(alert));

            var response = alertController.addAlert(alertCreationDTO);

            StepVerifier.create(response)
                    .expectNextMatches(httpResponse -> assertHttpStatusIs(201, httpResponse)
                            && assertFieldsMatch(alert, httpResponse))
                    .verifyComplete();
        }

        @Test
        @DisplayName("Deletes alert and returns response status 200 OK")
        void deletesAlertWhenAlertExists_andReturnsStatus200Ok() {
            when(alertService.deleteAlertById(anyString())).thenReturn(Mono.empty());

            var response = alertController.deleteAlert(anyString());

            StepVerifier.create(response)
                    .expectNextMatches(httpResponse -> assertHttpStatusIs(200, httpResponse))
                    .verifyComplete();
        }
    }

    // Private helper methods

    /**
     * Asserts whether the fields of the provided {@link Alert} object match the fields
     * of the {@link Alert} object in the HTTP response body.
     *
     * @param alert       The expected alert object.
     * @param httpResponse The HTTP response containing an alert object.
     * @return {@code true} if fields match, {@code false} otherwise.
     */
    private static boolean assertFieldsMatch(Alert alert, MutableHttpResponse<Alert> httpResponse) {
        try {
            assertEquals(alert, httpResponse.body());
            return true;
        } catch (AssertionError error) {
            return false;
        }
    }

    /**
     * Asserts whether the HTTP response status code matches the expected code.
     *
     * @param expected    The expected HTTP status code.
     * @param httpResponse The HTTP response to be verified.
     * @return {@code true} if status code matches, {@code false} otherwise.
     */
    private static boolean assertHttpStatusIs(int expected, MutableHttpResponse<?> httpResponse) {
        try {
            assertEquals(expected, httpResponse.getStatus().getCode());
            return true;
        } catch (AssertionError error) {
            return false;
        }
    }
}
