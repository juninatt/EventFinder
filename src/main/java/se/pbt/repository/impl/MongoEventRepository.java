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

@Singleton
public class MongoEventRepository implements EventRepository {

    private final MongoCollection<Document> collection;


    public MongoEventRepository(MongoClient mongoClient, String databaseName, String collectionName) {
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        this.collection = database.getCollection(collectionName);
    }

    @Override
    public Event save(Event event) {
        Document document = new Document();
        document.put("name", event.getName());
        document.put("description", event.getDescription());

        collection.insertOne(document);

        event.setId(document.getObjectId("_id").toString()); // Set the generated ID to the event
        return event;
    }

    @Override
    public List<Event> findAll() {
        List<Event> events = new ArrayList<>();
        for (Document document : collection.find()) {
            Event event = new Event();
            event.setId(document.getObjectId("_id").toString());
            event.setName(document.getString("name"));
            event.setDescription(document.getString("description"));
            events.add(event);
        }
        return events;
    }

    @Override
    public Optional<Event> findById(String id) {
        Document document = collection.find(new Document("_id", new ObjectId(id))).first();
            Event event = new Event();
            event.setId(document.getObjectId("_id").toString());
            event.setName(document.getString("name"));
            event.setDescription(document.getString("description"));
            return Optional.of(event);
    }

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
