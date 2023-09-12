package se.pbt.exception;

/**
 * Exception indicating database connection issues.
 */
public class DatabaseConnectionException extends RuntimeException {


    /**
     * Constructs the exception with a specified detail message.
     *
     * @param message Error message.
     */
    public DatabaseConnectionException(String message) {
        super(message);
    }

    /**
     * Constructs the exception with a message and an underlying cause.
     *
     * @param message Descriptive error message.
     * @param cause   Root cause of the exception.
     */
    public DatabaseConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
