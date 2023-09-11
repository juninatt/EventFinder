package se.pbt.config;

import io.micronaut.context.annotation.ConfigurationProperties;
import jakarta.validation.constraints.NotBlank;

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


    // Getters and setters
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }
}
