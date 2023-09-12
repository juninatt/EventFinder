package se.pbt.converter;

import org.bson.Document;
import org.bson.types.ObjectId;
import se.pbt.domain.Event;

import java.time.Instant;
import java.util.HashMap;

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
        document.put("links", new HashMap<>(event.getLinks()));
        return document;
    }

    /**
     * Converts a MongoDB {@link Document} into an {@link Event} object.
     * This method reads fields such as id, name, description, venue, date, category, duration, ticketPrice, and links from the document.
     *
     * @param document The MongoDB Document to be converted.
     * @return An Event object representation of the given MongoDB Document with all its fields populated.
     */
    public static Event fromDocument(Document document) {
        Event event = new Event();
        event.setId(document.getObjectId("_id").toString());
        event.setName(document.getString("name"));
        event.setVenue(document.getString("venue"));
        event.setDate(Instant.parse(document.getString("date")));
        event.setCategory(document.getString("category"));
        event.setDuration(document.getLong("duration"));
        event.setDescription(document.getString("description"));
        event.setTicketPrice(document.getDouble("ticketPrice"));
        setEventLinks(document, event);
        return event;
    }

    /**
     * Extracts the links from the given MongoDB {@link Document} and sets them to the provided {@link Event} object.
     * This method checks if the links from the document is of type {@link HashMap} before setting it to the event.
     *
     * @param document The MongoDB Document from which the links should be extracted.
     * @param event The Event object to which the links should be set.
     */
    private static void setEventLinks(Document document, Event event) {
        Object linksObj = document.get("links");
        if (linksObj instanceof HashMap) {
            event.setLinks((HashMap<String, String>) linksObj);
        }
    }
}
