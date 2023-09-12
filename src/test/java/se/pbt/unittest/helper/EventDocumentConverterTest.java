package se.pbt.unittest.helper;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.pbt.domain.Event;
import se.pbt.helper.EventDocumentConverter;

import java.time.Instant;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventDocumentConverterTest {

    @Test
    @DisplayName("Verify conversion from event to document")
    public void testToDocument() {
        Event event = new Event("Concert", "A big rock concert");
        event.setId("60a4b56f5b4b3f104c39e128");
        event.setVenue("Arena");
        event.setDate(Instant.parse("2021-05-12T10:15:30Z"));
        event.setCategory("Music");
        event.setDuration(3L);
        event.setDescription("A big rock concert in the Arena");
        event.setTicketPrice(49.99);
        HashMap<String, String> links = new HashMap<>();
        links.put("Official Site", "http://example.com");
        event.setLinks(links);

        Document doc = EventDocumentConverter.toDocument(event);

        assertEquals("60a4b56f5b4b3f104c39e128", doc.getObjectId("_id").toString());
        assertEquals("Concert", doc.getString("name"));
        assertEquals("Arena", doc.getString("venue"));
        assertEquals("2021-05-12T10:15:30Z", doc.getString("date"));
        assertEquals("Music", doc.getString("category"));
        assertEquals(3L, doc.getLong("duration"));
        assertEquals("A big rock concert in the Arena", doc.getString("description"));
        assertEquals(49.99, doc.getDouble("ticketPrice"), 0.01);
        assertEquals(links, doc.get("links"));
    }

    @Test
    @DisplayName("Verify conversion from document to event")
    public void testFromDocument() {
        Document doc = new Document();
        doc.put("_id", new ObjectId());
        doc.put("name", "Sample Event");
        doc.put("venue", "Sample Venue");
        doc.put("date", "2023-09-12T10:15:30Z");
        doc.put("category", "Sample Category");
        doc.put("duration", 3600L);
        doc.put("description", "Sample Description");
        doc.put("ticketPrice", 100.50);
        HashMap<String, String> links = new HashMap<>();
        links.put("Sample Link", "http://example.com");
        doc.put("links", links);

        Event convertedEvent = EventDocumentConverter.fromDocument(doc);

        assertEquals("Sample Event", convertedEvent.getName());
        assertEquals("Sample Venue", convertedEvent.getVenue());
        assertEquals(Instant.parse("2023-09-12T10:15:30Z"), convertedEvent.getDate());
        assertEquals("Sample Category", convertedEvent.getCategory());
        assertEquals(3600L, convertedEvent.getDuration());
        assertEquals("Sample Description", convertedEvent.getDescription());
        assertEquals(100.50, convertedEvent.getTicketPrice(), 0.001); // small delta for floating point comparison
        assertEquals(links, convertedEvent.getLinks());
    }
}
