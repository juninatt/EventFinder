package se.pbt.exception;

/**
 * Custom exception to indicate configuration validation errors.
 */
public class ConfigurationValidationException extends RuntimeException {

    /**
     * Constructs a new exception with a specified detail message.
     *
     * @param message Detailed message about the configuration issue.
     */
    public ConfigurationValidationException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with a specified detail message and cause.
     *
     * @param message Detailed message about the configuration issue.
     * @param cause   The underlying cause of this exception.
     */
    public ConfigurationValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
