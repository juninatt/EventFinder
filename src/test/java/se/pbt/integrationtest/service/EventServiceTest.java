package se.pbt.integrationtest.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.pbt.config.MongoConfig;
import se.pbt.domain.Event;
import se.pbt.repository.impl.MongoEventRepository;
import se.pbt.service.EventService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public class EventServiceTest {
    private final MongoEventRepository repository;
    private final EventService eventService;

    @Inject
    public EventServiceTest(MongoConfig config) {
        MongoClient mongoClient = MongoClients.create(config.getUri());
        repository = new MongoEventRepository(mongoClient, config);
        eventService = new EventService(repository);
    }

    @BeforeEach
    void setUp() {
        repository.clearAll();
    }

    @Test
    @DisplayName("Verify save event")
    void testSaveEvent() {
        Event event = new Event("Sample Event", "This is a sample event description");
        Event savedEvent = eventService.save(event);

        assertNotNull(savedEvent.getId());
        assertEquals(event.getName(), savedEvent.getName());
        assertEquals(event.getDescription(), savedEvent.getDescription());
    }

    @Test
    @DisplayName("Verify retrieve all events")
    void testFindAllEvents() {
        eventService.save(new Event("Event 1", "Description 1"));
        eventService.save(new Event("Event 2", "Description 2"));

        assertEquals(2, eventService.findAll().size());
    }

    @Test
    @DisplayName("Verify retrieve event by ID")
    void testFindEventById() {
        Event event = new Event("Event", "Description");
        Event savedEvent = eventService.save(event);

        Optional<Event> foundEvent = eventService.findById(savedEvent.getId());

        assertTrue(foundEvent.isPresent());
        assertEquals(event.getName(), foundEvent.get().getName());
        assertEquals(event.getDescription(), foundEvent.get().getDescription());
    }

    @Test
    @DisplayName("Verify delete event by ID")
    void testDeleteEventById() {
        Event event = new Event("Event", "Description");
        Event savedEvent = eventService.save(event);

        assertTrue(eventService.deleteById(savedEvent.getId()));
    }
}
