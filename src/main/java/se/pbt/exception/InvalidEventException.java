package se.pbt.exception;

import se.pbt.domain.Event;

/**
 * Custom exception to indicate when an {@link Event} is invalid.
 */
public class InvalidEventException extends RuntimeException {

    /**
     * Constructs a new exception with a specified detail message.
     *
     * @param message Detailed message about the invalid event.
     */
    public InvalidEventException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with a specified detail message and cause.
     *
     * @param message Detailed message about the invalid event.
     * @param cause   The underlying cause of this exception.
     */
    public InvalidEventException(String message, Throwable cause) {
        super(message, cause);
    }
}
