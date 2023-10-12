package se.pbt.socialalert.exception;

import io.micronaut.http.HttpStatus;
import se.pbt.socialalert.annotation.HttpStatusAnnotation;
import se.pbt.socialalert.model.entity.Alert;

/**
 * Exception that indicates an error occurred while attempting to save an {@link Alert}.
 */
@HttpStatusAnnotation(HttpStatus.INTERNAL_SERVER_ERROR)
public class AlertSavingException extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message The detail message explaining the reason for the exception.
     */
    public AlertSavingException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     *
     * @param message The detail message explaining the reason for the exception.
     * @param cause   The cause of the exception.
     */
    public AlertSavingException(String message, Throwable cause) {
        super(message, cause);
    }
}
