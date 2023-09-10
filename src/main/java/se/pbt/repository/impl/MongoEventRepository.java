package se.pbt.repository.impl;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import jakarta.inject.Singleton;
import org.bson.Document;
import org.bson.types.ObjectId;
import se.pbt.domain.Event;
import se.pbt.repository.EventRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import se.pbt.helper.EventDocumentConverter;

/**
 * Implementation of the {@link EventRepository} interface using MongoDB as the underlying datastore.
 * This repository handles CRUD operations for {@link Event} objects.
 */
@Singleton
public class MongoEventRepository implements EventRepository {

    private final MongoCollection<Document> collection;


    /**
     * Constructor initializing the MongoDB collection to be used for operations.
     *
     * @param mongoClient    The MongoDB client instance.
     * @param databaseName   The name of the MongoDB database.
     * @param collectionName The name of the MongoDB collection.
     */
    public MongoEventRepository(MongoClient mongoClient, String databaseName, String collectionName) {
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        this.collection = database.getCollection(collectionName);
    }

    /**
     * Saves an event object to the MongoDB collection.
     *
     * @param event The event to be saved.
     * @return The saved event with its generated ID.
     */
    @Override
    public Event save(Event event) {
        Document document = EventDocumentConverter.toDocument(event);
        collection.insertOne(document);
        event.setId(document.getObjectId("_id").toString());
        return event;
    }

    /**
     * Retrieves all events from the MongoDB collection.
     *
     * @return A list of all events.
     */
    @Override
    public List<Event> findAll() {
        List<Event> events = new ArrayList<>();
        for (Document document : collection.find()) {
            events.add(EventDocumentConverter.fromDocument(document));
        }
        return events;
    }

    /**
     * Finds an event by its ID.
     *
     * @param id The ID of the event to be retrieved.
     * @return An Optional containing the found event, or empty if no event was found.
     */
    @Override
    public Optional<Event> findById(String id) {
        Document document = collection.find(new Document("_id", new ObjectId(id))).first();
        if (document != null) {
            return Optional.of(EventDocumentConverter.fromDocument(document));
        }
        return Optional.empty();
    }

    /**
     * Deletes an event by its ID.
     *
     * @param id The ID of the event to be deleted.
     * @return True if the deletion was successful, false otherwise.
     */
    @Override
    public boolean deleteById(String id) {
        try {
            collection.deleteOne(new Document("_id", new ObjectId(id)));
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }
}
