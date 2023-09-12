package se.pbt.exception;

import io.micronaut.http.HttpStatus;
import se.pbt.annotation.HttpStatusAnnotation;

/**
 * Custom exception to indicate configuration validation errors.
 */
@HttpStatusAnnotation(HttpStatus.INTERNAL_SERVER_ERROR)
public class ConfigurationValidationException extends RuntimeException {

    /**
     * Constructs a new exception with a specified detail message.
     *
     * @param message Detailed message about the configuration issue.
     */
    public ConfigurationValidationException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with a specified detail message and cause.
     *
     * @param message Detailed message about the configuration issue.
     * @param cause   The underlying cause of this exception.
     */
    public ConfigurationValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
