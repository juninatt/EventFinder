package se.pbt.config;

import io.micronaut.context.annotation.ConfigurationProperties;
import jakarta.validation.constraints.NotBlank;
import se.pbt.exception.ConfigurationValidationException;

/**
 * Configuration properties for MongoDB connection and interactions.
 * Populated at runtime by Micronaut from the application's configuration.
 */
@ConfigurationProperties("mongodb")
public class MongoConfig {

    @NotBlank
    private String uri;
    @NotBlank
    private String database;
    @NotBlank
    private String collection;


    // Getters and setters needed for Micronaut to initialize database

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        if(uri == null || uri.trim().isEmpty()) {
            throw new ConfigurationValidationException("URI cannot be blank");
        }
        this.uri = uri;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        if(database == null || database.trim().isEmpty()) {
            throw new ConfigurationValidationException("Database cannot be blank");
        }
        this.database = database;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        if(collection == null || collection.trim().isEmpty()) {
            throw new ConfigurationValidationException("Collection cannot be blank");
        }
        this.collection = collection;
    }
}
