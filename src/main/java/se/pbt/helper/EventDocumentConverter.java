package se.pbt.helper;

import org.bson.Document;
import org.bson.types.ObjectId;
import se.pbt.domain.Event;

/**
 * Utility class that provides methods to convert between {@link Event} objects and MongoDB {@link Document} objects.
 */
public class EventDocumentConverter {

    /**
     * Converts an {@link Event} object into a MongoDB {@link Document}.
     *
     * @param event The event object to be converted.
     * @return A MongoDB Document representation of the given event.
     */
    public static Document toDocument(Event event) {
        Document document = new Document();
        if (event.getId() != null) {
            document.put("_id", new ObjectId(event.getId()));
        }
        document.put("name", event.getName());
        document.put("description", event.getDescription());
        return document;
    }

    /**
     * Converts a MongoDB {@link Document} into an {@link Event} object.
     *
     * @param document The MongoDB Document to be converted.
     * @return An event object representation of the given MongoDB Document.
     */
    public static Event fromDocument(Document document) {
        Event event = new Event();
        event.setId(document.getObjectId("_id").toString());
        event.setName(document.getString("name"));
        event.setDescription(document.getString("description"));
        return event;
    }
}
