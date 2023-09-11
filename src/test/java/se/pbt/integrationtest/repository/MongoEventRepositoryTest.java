package se.pbt.integrationtest.repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import jakarta.inject.Inject;
import org.junit.jupiter.api.*;
import se.pbt.domain.Event;
import se.pbt.repository.impl.MongoEventRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MongoEventRepositoryTest {

    private MongoClient mongoClient;
    @Inject
    private MongoEventRepository repository;

    @BeforeAll
    public void setupDatabase() {
        mongoClient = MongoClients.create();
        repository = new MongoEventRepository(mongoClient, "integrationTestDatabase", "eventCollection");
    }

    private Event createSampleEvent(String nameSuffix, String descriptionSuffix) {
        Event event = new Event();
        event.setName("Integration Test Event " + nameSuffix);
        event.setDescription("Integration Test Description " + descriptionSuffix);
        return event;
    }

    @Test
    @DisplayName("Verify save and retrieve all events")
    public void testSaveAndFindAll() {
        Event event = createSampleEvent("", "");

        Event savedEvent = repository.save(event);
        assertNotNull(savedEvent.getId());

        List<Event> events = repository.findAll();
        assertFalse(events.isEmpty());
    }

    @Test
    @DisplayName("Verify save and retrieve event by ID")
    public void testFindById() {
        Event event = createSampleEvent("for FindById", "for FindById");

        Event savedEvent = repository.save(event);
        assertNotNull(savedEvent.getId());

        Optional<Event> foundEvent = repository.findById(savedEvent.getId());
        assertTrue(foundEvent.isPresent());
        assertEquals(savedEvent.getName(), foundEvent.get().getName());
    }

    @Test
    @DisplayName("Verify save and delete event by ID")
    public void testDeleteById() {
        Event event = createSampleEvent("for Delete", "for Delete");

        Event savedEvent = repository.save(event);
        assertNotNull(savedEvent.getId());

        boolean isDeleted = repository.deleteById(savedEvent.getId());
        assertTrue(isDeleted);
    }

    @AfterAll
    public void cleanupDatabase() {
        mongoClient.close();
    }
}

