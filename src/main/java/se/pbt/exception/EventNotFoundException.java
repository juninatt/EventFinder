package se.pbt.exception;

import io.micronaut.http.HttpStatus;
import se.pbt.annotation.HttpStatusAnnotation;
import se.pbt.domain.Event;

/**
 * Custom exception to indicate when an {@link Event} is not found.
 */
@HttpStatusAnnotation(HttpStatus.NOT_FOUND)
public class EventNotFoundException extends RuntimeException {

    /**
     * Constructs a new exception with a detail message regarding the missing event.
     *
     * @param eventId The ID of the event that was not found.
     */
    public EventNotFoundException(String eventId) {
        super("No event found with ID: " + eventId);
    }

    /**
     * Constructs a new exception with a detail message and a specified cause.
     *
     * @param eventId The ID of the event that was not found.
     * @param cause   The underlying cause of this exception.
     */
    public EventNotFoundException(String eventId, Throwable cause) {
        super("No event found with ID: " + eventId, cause);
    }
}

