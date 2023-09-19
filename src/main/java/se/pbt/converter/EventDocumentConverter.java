package se.pbt.converter;

import org.bson.Document;
import org.bson.types.ObjectId;
import se.pbt.domain.Event;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Utility class that provides methods to convert between {@link Event} objects and MongoDB {@link Document} objects.
 */
public class EventDocumentConverter {

    /**
     * Converts an {@link Event} object into a MongoDB {@link Document}.
     * This method handles fields such as id, name, description, venue, date, category, duration, ticketPrice, and links.
     *
     * @param event The event object to be converted.
     * @return A MongoDB Document representation of the given event which includes all its fields.
     */
    public static Document toDocument(Event event) {
        Document document = new Document();
        if (event.getId() != null) {
            document.put("_id", new ObjectId(event.getId()));
        }
        document.put("name", event.getName());
        document.put("venue", event.getVenue());
        document.put("date", event.getDate().toString());
        document.put("category", event.getCategory());
        document.put("duration", event.getDuration());
        document.put("description", event.getDescription());
        document.put("ticketPrice", event.getTicketPrice());
        document.put("links", new ConcurrentHashMap<>(event.getLinks()));
        return document;
    }

    /**
     * Converts a MongoDB {@link Document} into an {@link Event} object.
     * This method reads fields such as id, name, description, venue, date, category, duration, ticketPrice,
     * and links from the document. Note: The date in the document should be in ISO-8601 format to be successfully parsed.
     *
     * @param document The MongoDB Document to be converted.
     * @return An Event object representation of the given MongoDB Document with all its fields populated.
     */
    public static Event fromDocument(Document document) {
        Event event = new Event(
                document.getString("name"),
                document.getString("venue"),
                Instant.parse(document.getString("date")),
                document.getString("category"),
                document.getLong("duration"),
                document.getString("description"),
                document.getDouble("ticketPrice"),
                extractEventLinks(document)
        );
        return new Event(event, document.getObjectId("_id").toString());
    }

    /**
     * Extracts the links from the given MongoDB {@link Document} and returns them as a {@link ConcurrentHashMap}.
     * The method assumes the links in the document are stored as a generic map and attempts to cast it to a
     * ConcurrentHashMap for use within the application.
     *
     * @param document The MongoDB Document from which the links should be extracted.
     * @return A ConcurrentHashMap containing the links or an empty map if no links are found.
     */
    private static ConcurrentHashMap<String, String> extractEventLinks(Document document) {
        Object linksObj = document.get("links");
        if (linksObj instanceof ConcurrentHashMap<?,?>) {
            return (ConcurrentHashMap<String, String>) linksObj;
        }
        return new ConcurrentHashMap<>();
    }
}
