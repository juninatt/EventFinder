package se.pbt.exception;

import io.micronaut.http.HttpStatus;
import se.pbt.annotation.HttpStatusAnnotation;

/**
 * Thrown to indicate that an event conversion operation failed.
 */
@HttpStatusAnnotation(value = HttpStatus.INTERNAL_SERVER_ERROR)
public final class EventConversionException extends RuntimeException {

    /**
     * Constructs a new event conversion exception with {@code null}
     * as its detail message.
     */
    public EventConversionException() {
        super();
    }

    /**
     * Constructs a new event conversion exception with the specified detail message.
     *
     * @param message the detail message
     */
    public EventConversionException(String message) {
        super(message);
    }

    /**
     * Constructs a new event conversion exception with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public EventConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}
