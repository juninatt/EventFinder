package se.pbt.integrationtest.repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.pbt.config.MongoConfig;
import se.pbt.domain.Event;
import se.pbt.repository.impl.MongoEventRepository;
import se.pbt.testobject.TestObjectCreator;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public class MongoEventRepositoryTest {

    private final MongoEventRepository repository;

    @Inject
    public MongoEventRepositoryTest(MongoConfig config) {
        MongoClient mongoClient = MongoClients.create(config.getUri());
        repository = new MongoEventRepository(mongoClient, config);
    }

    @Test
    @DisplayName("Verify save and retrieve all events")
    public void testSaveAndFindAll() {
        Event event = TestObjectCreator.createSampleEvent("", "");

        Event savedEvent = repository.save(event);
        assertNotNull(savedEvent.getId());

        List<Event> events = repository.findAll();
        assertFalse(events.isEmpty());
    }

    @Test
    @DisplayName("Verify save and retrieve event by ID")
    public void testFindById() {
        Event event = TestObjectCreator.createSampleEvent("for FindById", "for FindById");

        Event savedEvent = repository.save(event);
        assertNotNull(savedEvent.getId());

        Optional<Event> foundEvent = repository.findById(savedEvent.getId());
        assertTrue(foundEvent.isPresent());
        assertEquals(savedEvent.getName(), foundEvent.get().getName());
    }

    @Test
    @DisplayName("Verify save and delete event by ID")
    public void testDeleteById() {
        Event event = TestObjectCreator.createSampleEvent("for Delete", "for Delete");

        Event savedEvent = repository.save(event);
        assertNotNull(savedEvent.getId());

        boolean isDeleted = repository.deleteById(savedEvent.getId());
        assertTrue(isDeleted);
    }
}

