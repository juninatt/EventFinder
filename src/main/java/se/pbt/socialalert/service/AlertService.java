package se.pbt.socialalert.service;

import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.pbt.socialalert.converter.AlertConverter;
import se.pbt.socialalert.model.entity.Alert;
import se.pbt.socialalert.model.dto.AlertCreationDTO;
import se.pbt.socialalert.repository.AlertRepository;
import se.pbt.socialalert.exception.AlertNotFoundException;

import java.util.Objects;

/**
 * Service class handling business logic for managing {@link Alert} entities in a reactive manner.
 * This service utilizes reactive types (Mono and Flux) for asynchronous and non-blocking operations.
 */
@Singleton
public class AlertService {

    private final AlertRepository alertRepository;

    /**
     * Constructor for initializing the AlertService with the provided {@link AlertRepository} instance.
     *
     * @param alertRepository The AlertRepository instance used for database interactions.
     */
    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    /**
     * Creates a new {@link Alert} based on the provided AlertCreationDTO information.
     *
     * @param alertData The AlertCreationDTO object containing alert information.
     * @return A Mono emitting the newly created Alert.
     */
    public Mono<Alert> createAlert(AlertCreationDTO alertData) {
        return alertRepository.save(AlertConverter.toEntity(alertData));
    }

    /**
     * Retrieves all {@link Alert} objects from the database asynchronously.
     *
     * @return A Flux emitting a list of all available alerts.
     */
    public Flux<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }

    /**
     * Retrieves an {@link Alert} by its unique ID asynchronously.
     *
     * @param id The unique identifier of the desired alert.
     * @return A Mono containing the alert if found, or empty otherwise.
     */
    public Mono<Alert> getAlertById(String id) {
        try {
            Objects.requireNonNull(id, "Event ID must not be null");
            return alertRepository.findById(id)
                    .switchIfEmpty(Mono.error(new AlertNotFoundException("Event not found with ID: " + id)));
        } catch (Exception e) {
            return Mono.error(new AlertNotFoundException("Event not found"));
        }
    }

    /**
     * Updates an existing {@link Alert} with new information asynchronously.
     *
     * @param id           The unique identifier of the alert to be updated.
     * @param updatingInfo The AlertCreationDTO object containing updated alert information.
     * @return A Mono emitting the updated Alert.
     */
    public Mono<Alert> updateEvent(String id, AlertCreationDTO updatingInfo) {
        return getAlertById(id)
                .flatMap(existingEvent -> {
                    Alert updatedAlert = AlertConverter.toEntity(id, updatingInfo);
                    return alertRepository.save(updatedAlert);
                });
    }

    /**
     * Deletes an {@link Alert} by its unique ID asynchronously.
     *
     * @param id The unique identifier of the alert to be deleted.
     * @return A Mono representing the completion of the deletion operation.
     */
    public Mono<Void> deleteAlertById(String id) {
        return alertRepository.findById(id)
                .flatMap(existingEvent -> alertRepository.deleteById(id))
                .switchIfEmpty(Mono.empty());
    }

    /**
     * Clears all {@link Alert} objects from the database asynchronously.
     *
     * @return A Mono representing the completion of the deletion operation.
     */
    public Mono<Void> deleteAllEvents() {
        alertRepository.deleteAll();
        return Mono.empty();
    }
}

