package se.pbt.exception;

import io.micronaut.http.HttpStatus;
import se.pbt.annotation.HttpStatusAnnotation;

/**
 * Custom exception to indicate issues when deleting an event.
 */
@HttpStatusAnnotation(HttpStatus.INTERNAL_SERVER_ERROR)
public class EventDeletionException extends RuntimeException {

    /**
     * Constructs a new exception with a detailed message about the deletion error.
     *
     * @param eventId The ID of the event that couldn't be deleted.
     */
    public EventDeletionException(String eventId) {
        super("Error deleting event with ID: " + eventId);
    }

    /**
     * Constructs a new exception with a detailed message and a specified cause.
     *
     * @param eventId The ID of the event that couldn't be deleted.
     * @param cause   The underlying cause of this exception.
     */
    public EventDeletionException(String eventId, Throwable cause) {
        super("Error deleting event with ID: " + eventId, cause);
    }
}
