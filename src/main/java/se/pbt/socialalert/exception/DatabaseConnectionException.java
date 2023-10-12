package se.pbt.socialalert.exception;

import io.micronaut.http.HttpStatus;
import se.pbt.socialalert.annotation.HttpStatusAnnotation;

/**
 * Exception indicating database connection issues.
 */
@HttpStatusAnnotation(HttpStatus.INTERNAL_SERVER_ERROR)
public class DatabaseConnectionException extends RuntimeException {


    /**
     * Constructs the exception with a specified detail message.
     *
     * @param message The detail message.
     */
    public DatabaseConnectionException(String message) {
        super(message);
    }

    /**
     * Constructs the exception with a message and an underlying cause.
     *
     * @param message The detail message.
     * @param cause   Root cause of the exception.
     */
    public DatabaseConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
