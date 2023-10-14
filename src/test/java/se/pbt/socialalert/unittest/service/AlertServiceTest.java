package se.pbt.socialalert.unittest.service;

import org.junit.jupiter.api.AfterEach;
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
import se.pbt.socialalert.model.entity.Alert;
import se.pbt.socialalert.model.dto.AlertCreationDTO;
import se.pbt.socialalert.repository.AlertRepository;
import se.pbt.socialalert.testobject.TestObjectCreator;
import se.pbt.socialalert.service.AlertService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("AlertService Unit Tests:")
@ExtendWith(MockitoExtension.class)
public class AlertServiceTest {
    @Mock
    private AlertRepository alertRepository;
    @InjectMocks
    private AlertService alertService;

    private static String testTrigger;
    private static Alert testAlert;

    @BeforeAll
    static void init() {
        testTrigger = "Heroin";
        testAlert = TestObjectCreator.alert(testTrigger);
    }
    @AfterEach
    void cleanUp() {
        alertRepository.deleteAll();
    }

    @Test
    @DisplayName("Saving an alert returns the saved alert")
    public void testSaveAlert_returnsSavedAlert() {
        when(alertRepository.save(any(Alert.class))).thenReturn(Mono.just(testAlert));

        Mono<Alert> savedEvent = alertService.createAlert(TestObjectCreator.alertCreationDTO(testTrigger));

        StepVerifier.create(savedEvent)
                .expectNext(testAlert)
                .verifyComplete();
    }

    @Test
    @DisplayName("Retrieving all alerts returns a list containing stored alerts")
    public void testGetAllAlerts_retursAllAlert() {
        String trigger = "Free Tibet";
        Alert alert = TestObjectCreator.alert(trigger);

        when(alertRepository.findAll()).thenReturn(Flux.just(testAlert, alert));

        Flux<Alert> foundAlerts = alertService.getAllAlerts();

        StepVerifier.create(foundAlerts)
                .expectNext(testAlert, alert)
                .verifyComplete();
    }

    @Test
    @DisplayName("Finding an alert by ID returns the corresponding alert")
    public void testFindAlertById_returnsCorrespondingAlert() {
        when(alertRepository.findById(anyString())).thenReturn(Mono.just(testAlert));

        Mono<Alert> foundAlert = alertService.getAlertById(testAlert.getId());

        StepVerifier.create(foundAlert)
                .expectNext(testAlert)
                .verifyComplete();
    }

    @Test
    @DisplayName("Deleting an alert by ID completes successfully")
    public void testDeleteAlertById_completesSuccessfully() {
        when(alertRepository.findById(anyString())).thenReturn(Mono.empty());

        Mono<Void> deleteResult = alertService.deleteAlertById(testAlert.getId());

        StepVerifier.create(deleteResult)
                .verifyComplete();
    }

    @Test
    @DisplayName("Creating an alert returns the created alert with generated ID")
    void testCreateEvent_returnsCreatedAlert_withCorrectValues() {
        AlertCreationDTO newAlertData = TestObjectCreator.alertCreationDTO(testTrigger);
        Alert expectedAlert = TestObjectCreator.alert(testTrigger);

        when(alertRepository.save(any())).thenReturn(Mono.just(expectedAlert));

        StepVerifier.create(alertService.createAlert(newAlertData))
                .expectNextMatches(alert -> {
                    assertNotNull(alert.getId());
                    assertEquals(expectedAlert.getTrigger(), alert.getTrigger());
                    assertEquals(expectedAlert.getIpAddress(), alert.getIpAddress());
                    return true;
                })
                .verifyComplete();

        verify(alertRepository, times(1)).save(any());
    }
}
