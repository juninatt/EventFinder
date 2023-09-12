package se.pbt.testobject;

import se.pbt.domain.Event;

import java.time.Instant;
import java.util.HashMap;

public class TestObjectCreator {

    /**
     * Creates a sample Event object with populated fields for testing purposes.
     *
     * @param name A suffix to append to the event's name.
     * @param description A suffix to append to the event's description.
     * @return A populated Event object.
     */
    public static Event createSampleEvent(String name, String description) {
        Event event = new Event();
        event.setName("Integration Test Event " + name);
        event.setDescription("Integration Test Description " + description);

        // Sample data for the new fields
        event.setVenue("Sample Venue " + name);
        event.setDate(Instant.now()); // Assuming java.time.Instant has been imported
        event.setCategory("Sample Category");
        event.setDuration(120L); // Sample duration in minutes
        event.setTicketPrice(50.0); // Sample ticket price
        HashMap<String, String> links = new HashMap<>();
        links.put("SampleLink", "http://example.com");
        event.setLinks(links);

        return event;
    }
}
