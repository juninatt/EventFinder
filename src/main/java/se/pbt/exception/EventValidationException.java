package se.pbt.exception;

import io.micronaut.http.HttpStatus;
import se.pbt.annotation.HttpStatusAnnotation;
import se.pbt.domain.Event;

/**
 * Custom exception to indicate when an {@link Event} is invalid.
 */
@HttpStatusAnnotation(HttpStatus.BAD_REQUEST)
public class EventValidationException extends RuntimeException {

    /**
     * Constructs a new exception with a specified detail message.
     *
     * @param message Detailed message about the invalid event.
     */
    public EventValidationException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with a specified detail message and cause.
     *
     * @param message Detailed message about the invalid event.
     * @param cause   The underlying cause of this exception.
     */
    public EventValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
