package se.pbt.socialalert.unittest.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@DisplayName("EventController Unit Tests")
@ExtendWith(MockitoExtension.class)
class AlertControllerTest {
    @Mock
    private AlertService alertService;
    @InjectMocks
    private AlertController alertController;

    private static String testEventName, testDTOName;

    @BeforeAll
    static void init() {
        testEventName = "Controller Integration Test Event";
        testDTOName = "Controller Integration Test DTO";
    }

    @Test
    @DisplayName("Retrieving all events returns status 200 OK with list containing stored event")
    void testListEvents() {
        Alert alert = TestObjectCreator.socialAlert(testEventName);

        when(alertService.getAllAlerts()).thenReturn(Flux.just(alert));

        var response = alertController.getAlerts();

        StepVerifier.create(response)
                .expectNextMatches(httpResponse -> {
                    assertEquals(200, httpResponse.getStatus().getCode());
                    assertEquals(alert, httpResponse.body());
                    return  true;
                })
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Creating event returns status 201 CREATED with created event")
    void testAddEvent() {
        AlertCreationDTO alertCreationDTO = TestObjectCreator.alertCreationDTO(testDTOName);
        Alert alert = TestObjectCreator.socialAlert(testEventName);
        when(alertService.createAlert(alertCreationDTO)).thenReturn(Mono.just(alert));

        var response = alertController.addAlert(alertCreationDTO);

        StepVerifier.create(response)
                .expectNextMatches(httpResponse -> {
                    assertEquals(201, httpResponse.status().getCode());
                    assertEquals(alert, httpResponse.body());
                    return true;
                })
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Retrieve event bt ID returns status 200 OK with retrieved event")
    void testGetById() {
        Alert alert = TestObjectCreator.socialAlert(testEventName);
        when(alertService.getAlertById(anyString())).thenReturn(Mono.just(alert));

        var response = alertController.getAlert(alert.getId());

        StepVerifier.create(response)
                .expectNextMatches(httpResponse -> {
                    assertEquals(200, httpResponse.status().getCode());
                    assertEquals(alert, httpResponse.body());
                    return true;
                })
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Deleting event returns status 200 OK")
    void testDeleteById() {
        when(alertService.deleteAlertById(anyString())).thenReturn(Mono.empty());

        var response = alertController.deleteAlert(anyString());

        StepVerifier.create(response)
                .expectNextMatches(res -> {
                    assertEquals(200, res.status().getCode());
                return true;
                })
                .expectComplete()
                .verify();
    }
}
