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
import se.pbt.testobject.TestObjectCreator;

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
    @DisplayName("Verify save event saves and returns correct object")
    void testSaveEvent() {
        Event event = TestObjectCreator.createSampleEvent("Jul på Liseberg", "Jolly");
        Event savedEvent = eventService.save(event);

        assertNotNull(savedEvent.getId());
        assertEquals(event.getName(), savedEvent.getName());
        assertEquals(event.getDescription(), savedEvent.getDescription());
    }

    @Test
    @DisplayName("Verify find all retrieves all events")
    void testFindAllEvents() {
        eventService.save(TestObjectCreator.createSampleEvent("Way out West", "Rainy festival"));
        eventService.save(TestObjectCreator.createSampleEvent("Book Reading", "Cozy"));

        assertEquals(2, eventService.findAll().size());
    }

    @Test
    @DisplayName("Verify find by ID retrieves correct event")
    void testFindEventById() {
        Event event = TestObjectCreator.createSampleEvent("Kulturkalaset", "Gratis");
        Event savedEvent = eventService.save(event);

        Optional<Event> foundEvent = eventService.findById(savedEvent.getId());

        assertTrue(foundEvent.isPresent());
        assertEquals(event.getName(), foundEvent.get().getName());
        assertEquals(event.getDescription(), foundEvent.get().getDescription());
    }

    @Test
    @DisplayName("Verify delete by ID deletes event")
    void testDeleteEventById() {
        Event event = TestObjectCreator.createSampleEvent("Göteborg 400 år", "Once in a lifetime");
        Event savedEvent = eventService.save(event);

        assertTrue(eventService.deleteById(savedEvent.getId()));
    }
}
