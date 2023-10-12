package se.pbt.socialalert.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.pbt.socialalert.model.entity.Alert;

/**
 * Represents the data access layer for {@link Alert} entities in a reactive programming paradigm.
 * Provides basic CRUD operations for managing alerts using reactive types (Mono and Flux).
 */
public interface AlertRepository {

    /**
     * Persists the given {@link Alert} information asynchronously to the repository.
     *
     * @param alert The alert object to be saved.
     * @return A Mono emitting the saved alert with any additional set properties (e.g., id).
     */
    Mono<Alert> save(Alert alert);

    /**
     * Retrieves an {@link Alert} asynchronously from the repository based on the provided ID.
     *
     * @param id The unique identifier of the desired alert.
     * @return A Mono containing the alert if found, or empty otherwise.
     */
    Mono<Alert> findById(String id);

    /**
     * Fetches all the {@link Alert} asynchronously from the repository.
     *
     * @return A Flux emitting a list of all available alerts.
     */
    Flux<Alert> findAll();

    /**
     * Deletes an {@link Alert} asynchronously from the repository based on the provided ID.
     *
     * @param id The unique identifier of the alert to be deleted.
     * @return A Mono emitting {@code true} if the alert was deleted successfully, {@code false} otherwise.
     */
    Mono<Void> deleteById(String id);

    /**
     * Deletes all {@link Alert} asynchronously from the repository.
     *
     * @return A Mono indicating the completion of the deletion process.
     */
    Mono<Void> deleteAll();
}
