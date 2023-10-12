package se.pbt.socialalert.repository.impl;

import com.mongodb.client.*;
import jakarta.inject.Singleton;
import org.bson.Document;
import org.bson.types.ObjectId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.pbt.socialalert.config.MongoConfig;
import se.pbt.socialalert.converter.AlertConverter;
import se.pbt.socialalert.model.entity.Alert;
import se.pbt.socialalert.exception.AlertDeletionException;
import se.pbt.socialalert.exception.AlertNotFoundException;
import se.pbt.socialalert.exception.AlertSavingException;
import se.pbt.socialalert.repository.AlertRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link AlertRepository} interface using MongoDB as the underlying datastore.
 * This repository handles CRUD operations for {@link Alert} objects.
 */
@Singleton
public class MongoAlertRepository implements AlertRepository {

    private final MongoCollection<Document> collection;

    /**
     * Constructor initializing the MongoDB collection to be used for operations.
     *
     * @param mongoClient The MongoDB client instance.
     * @param config      The configuration object containing MongoDB settings.
     */
    public MongoAlertRepository(MongoClient mongoClient, MongoConfig config) {
        MongoDatabase database = mongoClient.getDatabase(config.getDatabase());
        this.collection = database.getCollection(config.getCollection());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mono<Alert> save(Alert alert) {
        Document document = AlertConverter.toDocument(alert);
        return Mono.fromRunnable(() -> {
            try {
                collection.insertOne(document);
            } catch (Exception exception) {
                throw new AlertSavingException("Problem saving Alert. Save aborted");
            }
        }).thenReturn(AlertConverter.toEntity(document));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Flux<Alert> findAll() {
        return Flux.defer(() -> {
            List<Alert> alerts = new ArrayList<>();
            try (MongoCursor<Document> cursor = collection.find().iterator()) {
                while (cursor.hasNext()) {
                    Document document = cursor.next();
                    Alert alert = AlertConverter.toEntity(document);
                    alerts.add(alert);
                }
            }
            return Flux.fromIterable(alerts);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mono<Alert> findById(String id) {
        return Mono.fromCallable(() -> {
            Document document = collection.find(new Document("_id", new ObjectId(id))).first();
            if (document == null) {
                throw new AlertNotFoundException(id);
            }
            return AlertConverter.toEntity(document);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mono<Void> deleteById(String id) {
        return Mono.defer(() -> {
            try {
                collection.deleteOne(new Document("_id", new ObjectId(id)));
                return Mono.empty(); // Complete without emitting any events
            } catch (RuntimeException exception) {
                return Mono.error(new AlertDeletionException("Failed to delete event with ID: " + id, exception.getCause()));
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mono<Void> deleteAll() {
        return Mono.fromCallable(() -> {
            collection.drop();
            return true;
        }).then(Mono.empty());
    }
}
