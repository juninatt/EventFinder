package se.pbt.socialalert.exception;

import io.micronaut.http.HttpStatus;
import se.pbt.socialalert.annotation.HttpStatusAnnotation;
import se.pbt.socialalert.model.entity.Alert;

/**
 * Thrown to indicate that an {@link Alert} conversion operation failed.
 */
@HttpStatusAnnotation(value = HttpStatus.INTERNAL_SERVER_ERROR)
public final class AlertConversionException extends RuntimeException {

    /**
     * Constructs a new exception without message.
     */
    public AlertConversionException() {
        super();
    }

    /**
     * Constructs a new exception with a detailed message about the conversion error.
     *
     * @param message The detail message
     */
    public AlertConversionException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with a detailed message and cause of the conversion error.
     *
     * @param message The detail message
     * @param cause The cause
     */
    public AlertConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}
