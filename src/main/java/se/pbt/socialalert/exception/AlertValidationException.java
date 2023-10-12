package se.pbt.socialalert.exception;

import io.micronaut.http.HttpStatus;
import se.pbt.socialalert.annotation.HttpStatusAnnotation;
import se.pbt.socialalert.model.entity.Alert;

/**
 * Custom exception to indicate when an {@link Alert} is invalid.
 */
@HttpStatusAnnotation(HttpStatus.BAD_REQUEST)
public class AlertValidationException extends RuntimeException {

    /**
     * Constructs a new exception with a specified detail message.
     *
     * @param message The detail message.
     */
    public AlertValidationException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with a specified detail message and cause.
     *
     * @param message The detail message.
     * @param cause   The underlying cause of this exception.
     */
    public AlertValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
