package se.pbt.socialalert.exception;

import io.micronaut.http.HttpStatus;
import se.pbt.socialalert.annotation.HttpStatusAnnotation;
import se.pbt.socialalert.model.entity.Alert;

/**
 * Custom exception to indicate when an {@link Alert} is not found.
 */
@HttpStatusAnnotation(HttpStatus.NOT_FOUND)
public class AlertNotFoundException extends RuntimeException {

    /**
     * Constructs a new exception with a detail message regarding the missing {@link Alert}.
     *
     * @param message A description of the error.
     */
    public AlertNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with a detail message and a specified cause.
     *
     * @param message A description of the error.
     * @param cause   The underlying cause of this exception.
     */
    public AlertNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

