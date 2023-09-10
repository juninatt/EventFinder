package se.pbt.helper;

import org.bson.Document;
import org.bson.types.ObjectId;
import se.pbt.domain.Event;

public class EventDocumentConverter {

    public static Document toDocument(Event event) {
        Document document = new Document();
        if (event.getId() != null) {
            document.put("_id", new ObjectId(event.getId()));
        }
        document.put("name", event.getName());
        document.put("description", event.getDescription());
        return document;
    }

    public static Event fromDocument(Document document) {
        Event event = new Event();
        event.setId(document.getObjectId("_id").toString());
        event.setName(document.getString("name"));
        event.setDescription(document.getString("description"));
        return event;
    }
}
