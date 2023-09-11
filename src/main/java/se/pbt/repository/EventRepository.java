package se.pbt.repository;

import se.pbt.domain.Event;

import java.util.List;
import java.util.Optional;

/**
 * Represents the data access layer for {@link Event} entities.
 * Provides basic CRUD operations for managing events.
 */
public interface EventRepository {

    /**
     * Persists the given event to the repository.
     *
     * @param event The event entity to be saved.
     * @return The saved event with any additional set properties (e.g., id).
     */
    Event save(Event event);

    /**
     * Retrieves an event from the repository based on the provided ID.
     *
     * @param id The unique identifier of the desired event.
     * @return An {@link Optional} containing the event if found, or empty otherwise.
     */
    Optional<Event> findById(String id);

    /**
     * Fetches all the events from the repository.
     *
     * @return A list of all available events.
     */
    List<Event> findAll();

    /**
     * Deletes an event from the repository based on the provided ID.
     *
     * @param id The unique identifier of the event to be deleted.
     * @return {@code true} if the event was deleted successfully, {@code false} otherwise.
     */
    boolean deleteById(String id);

    void clearAll();
}
