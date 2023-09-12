package se.pbt.exception;

/**
 * Custom exception to indicate issues when deleting an event.
 */
public class EventDeletionException extends RuntimeException {

    /**
     * Constructs a new exception with a detailed message about the deletion error.
     *
     * @param eventId The ID of the event that couldn't be deleted.
     */
    public EventDeletionException(String eventId) {
        super("Error deleting event with ID: " + eventId);
    }

    /**
     * Constructs a new exception with a detailed message and a specified cause.
     *
     * @param eventId The ID of the event that couldn't be deleted.
     * @param cause   The underlying cause of this exception.
     */
    public EventDeletionException(String eventId, Throwable cause) {
        super("Error deleting event with ID: " + eventId, cause);
    }
}
