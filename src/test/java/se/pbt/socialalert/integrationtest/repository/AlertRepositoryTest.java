package se.pbt.socialalert.integrationtest.repository;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.*;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import se.pbt.socialalert.model.entity.Alert;
import se.pbt.socialalert.repository.AlertRepository;
import se.pbt.socialalert.testobject.TestObjectCreator;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
@DisplayName("AlertRepository Integration Test:")
public class AlertRepositoryTest {
    @Inject
    private AlertRepository alertRepository;


    @AfterEach
    void cleanup() {
        alertRepository.deleteAll().block();
    }


    @Test
    @DisplayName("Saves and retrieves all existing alerts")
    public void testSaveAndFindAll_savesAndReturnsCorrectValues() {
        Alert alert = TestObjectCreator.socialAlert(null);

        Alert savedAlert = alertRepository.save(alert).block();
        assertNotNull(savedAlert);
        assertNotNull(savedAlert.getId());

        Flux<Alert> events = alertRepository.findAll();
        assertNotNull(events);

        Optional<Alert> foundEvent = events.toStream().findFirst();
        assertTrue(foundEvent.isPresent());
        assertEquals(savedAlert.getId(), foundEvent.get().getId());
        assertEquals(alert.getTrigger(), foundEvent.get().getTrigger());
        assertEquals(alert.getIpAddress(), foundEvent.get().getIpAddress());
    }

    @Test
    @DisplayName("Saves and retrieves alert by ID")
    public void testFindById() {
        Alert alert = TestObjectCreator.socialAlert(null);
        Alert savedAlert = alertRepository.save(alert).block();

        Optional<Alert> foundEvent = alertRepository.findById(savedAlert.getId()).blockOptional();
        assertTrue(foundEvent.isPresent());
        assertEquals(savedAlert.getTrigger(), foundEvent.get().getTrigger());
    }

    @Test
    @DisplayName("Saves and deletes alert by ID")
    public void testDeleteById() {
        Alert alert = TestObjectCreator.socialAlert(null);
        Alert savedAlert = alertRepository.save(alert).block();

        StepVerifier.create(alertRepository.deleteById(savedAlert.getId()))
                .verifyComplete();

        StepVerifier.create(alertRepository.findAll())
                .expectNextCount(0)
                .verifyComplete();
    }
}
