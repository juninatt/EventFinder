package se.pbt.exception;

/**
 * Exception that indicates an error occurred while attempting to save an event.
 */
public class EventSavingException extends RuntimeException {

    /**
     * Constructs a new EventSavingException with the specified detail message.
     *
     * @param message The detail message explaining the reason for the exception.
     */
    public EventSavingException(String message) {
        super(message);
    }

    /**
     * Constructs a new EventSavingException with the specified detail message and cause.
     *
     * @param message The detail message explaining the reason for the exception.
     * @param cause   The cause of the exception (which is saved for later retrieval by the {@link #getCause()} method).
     */
    public EventSavingException(String message, Throwable cause) {
        super(message, cause);
    }
}
