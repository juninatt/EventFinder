package se.pbt.unittest.converter;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.pbt.converter.EventConverter;
import se.pbt.domain.Event;
import se.pbt.testobject.TestObjectCreator;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventConverterTest {

    @Test
    @DisplayName("Verify conversion from event to document")
    public void testToDocument() {
        // Create test object
        Event testEvent = TestObjectCreator.createSampleEvent("Concert", "A big rock concert in the Arena");

        Document doc = EventConverter.toDocument(testEvent);

        assertEquals(testEvent.getName(), doc.getString("name"));
        assertEquals(testEvent.getVenue(), doc.getString("venue"));
        assertEquals(testEvent.getDate().toString(), doc.getString("date"));
        assertEquals(testEvent.getCategory(), doc.getString("category"));
        assertEquals(testEvent.getDuration(), doc.getLong("duration"));
        assertEquals(testEvent.getDescription(), doc.getString("description"));
        assertEquals(testEvent.getTicketPrice(), doc.getDouble("ticketPrice"), 0.01);
        assertEquals(testEvent.getLinks(), doc.get("links"));
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
        ConcurrentHashMap<String, String> links = new ConcurrentHashMap<>();
        links.put("Sample Link", "http://example.com");
        doc.put("links", links);

        Event convertedEvent = EventConverter.toEvent(doc);

        assertEquals("Sample Event", convertedEvent.getName());
        assertEquals("Sample Venue", convertedEvent.getVenue());
        assertEquals(Instant.parse("2023-09-12T10:15:30Z"), convertedEvent.getDate());
        assertEquals("Sample Category", convertedEvent.getCategory());
        assertEquals(3600L, convertedEvent.getDuration());
        assertEquals("Sample Description", convertedEvent.getDescription());
        assertEquals(100.50, convertedEvent.getTicketPrice(), 0.001); // Small delta for floating point comparison
        assertEquals(links, convertedEvent.getLinks());
    }
}
