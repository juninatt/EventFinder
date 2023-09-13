package se.pbt.testobject;

import se.pbt.domain.Event;
import se.pbt.dto.EventDTO;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * This class provides utility methods to create test objects for various classes
 * like {@link Event} and {@link EventDTO}.
 * The purpose is to facilitate easy testing by generating sample data.
 */
public class TestObjectCreator {

    /**
     * Creates a sample {@link Event} object with populated fields for testing purposes.
     *
     * @param name The event name.
     * @param description A description of the event.
     * @return A populated Event object.
     */
    public static Event createSampleEvent(String name, String description) {
        Event event = new Event();
        event.setName("Integration Test Event " + name);
        event.setDescription("Integration Test Description " + description);

        event.setVenue("Sample Venue " + name);
        event.setDate(Instant.now());
        event.setCategory("Sample Category");
        event.setDuration(120L);
        event.setTicketPrice(50.0);
        HashMap<String, String> links = new HashMap<>();
        links.put("SampleLink", "http://example.com");
        event.setLinks(links);

        return event;
    }

    /**
     * Creates a sample {@link EventDTO} object with populated fields for testing purposes.
     *
     * @param name The name of the event.
     * @param description A description of the event.
     * @return A populated EventDTO object.
     */
    public static EventDTO createSampleEventDTO(String name, String description) {
        return new EventDTO(
                "Integration Test Event " + name,
                "Integration Test Description " + description,
                Instant.now(),
                "Integration Test Venue " + name,
                120L,
                "Integration Test Category " + name,
                99.99,
                Map.of("facebook", "facebook.com/event" + name)
        );
    }
}
