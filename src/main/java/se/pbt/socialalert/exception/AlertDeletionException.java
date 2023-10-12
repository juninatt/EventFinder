package se.pbt.socialalert.exception;

import io.micronaut.http.HttpStatus;
import se.pbt.socialalert.annotation.HttpStatusAnnotation;
import se.pbt.socialalert.model.entity.Alert;

/**
 * Custom exception to indicate issues when deleting an {@link Alert}.
 */
@HttpStatusAnnotation(HttpStatus.INTERNAL_SERVER_ERROR)
public class AlertDeletionException extends RuntimeException {

    /**
     * Constructs a new exception with a detail message about the deletion error.
     *
     * @param message The detail message.
     */
    public AlertDeletionException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with a detailed message and a specified cause.
     *
     * @param message The detail message.
     * @param cause   The underlying cause of this exception.
     */
    public AlertDeletionException(String message, Throwable cause) {
        super(message, cause);
    }
}
