package se.pbt.service;

import jakarta.inject.Singleton;
import se.pbt.domain.Event;
import se.pbt.repository.impl.MongoEventRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service class responsible for handling CRUD operations related to {@link Event} entities.
 * This service acts as a mediator between the controller and the repository layers.
 */
@Singleton
public class EventService {

    private final MongoEventRepository repository;

    /**
     * Constructs an instance of the EventService.
     *
     * @param repository the {@link MongoEventRepository} to be used by this service
     */
    public EventService(MongoEventRepository repository) {
        this.repository = repository;
    }

    /**
     * Saves an event to the repository.
     *
     * @param event the {@link Event} to be saved
     * @return the saved {@link Event} with any additional data populated by the repository, such as ID
     */
    public Event save(Event event) {
        return repository.save(event);
    }

    /**
     * Retrieves all events from the repository.
     *
     * @return a list of all {@link Event} entities stored in the repository
     */
    public List<Event> findAll() {
        return repository.findAll();
    }

    /**
     * Retrieves an event from the repository by its ID.
     *
     * @param id the ID of the event to retrieve
     * @return an {@link Optional} containing the {@link Event} if found, or empty if not found
     */
    public Optional<Event> findById(String id) {
        return repository.findById(id);
    }

    /**
     * Deletes an event from the repository by its ID.
     *
     * @param id the ID of the event to delete
     * @return true if the event was deleted successfully, false otherwise
     */
    public boolean deleteById(String id) {
        return repository.deleteById(id);
    }
}
