package se.pbt.testobject;

import se.pbt.domain.Event;
import se.pbt.dto.EventDTO;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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
        String fullName = "Integration Test Event " + name;
        String fullDescription = "Integration Test Description " + description;
        String venue = "Sample Venue " + name;
        Instant date = Instant.now();
        String category = "Sample Category";
        long duration = 120L;
        double ticketPrice = 50.0;
        ConcurrentHashMap<String, String> links = new ConcurrentHashMap<>();
        links.put("SampleLink", "http://example.com");

        return new Event(fullName, venue, date, category, duration, fullDescription, ticketPrice, links);
    }

    /**
     * Creates a sample {@link EventDTO} object with populated fields for testing purposes.
     *
     * @param name The name of the event.
     * @param description A description of the event.
     * @return A populated EventDTO object.
     */
    public static EventDTO createSampleEventDTO(String name, String description) {
        String eventName = "Integration Test Event " + name;
        String eventDescription = "Integration Test Description " + description;
        Instant currentDate = Instant.now();
        String eventVenue = "Integration Test Venue " + name;
        long eventDuration = 120L;
        String eventCategory = "Integration Test Category " + name;
        double eventPrice = 99.99;
        ConcurrentMap<String, String> eventLinks = new ConcurrentHashMap<>();
        eventLinks.put("facebook", "facebook.com/event" + name);

        return new EventDTO(
                eventName,
                eventDescription,
                currentDate,
                eventVenue,
                eventDuration,
                eventCategory,
                eventPrice,
                eventLinks
        );
    }

    /**
     * Creates a sample {@link Event} object with pre-defined test values for testing purposes.
     *
     * @return A populated {@link Event} object with test values.
     */
    public static Event createSampleEvent() {
        String fullName = "Integration Test Event";
        String fullDescription = "Integration Test Description";
        String venue = "Sample Venue ";
        Instant date = Instant.now();
        String category = "Sample Category";
        long duration = 120L;
        double ticketPrice = 50.0;
        ConcurrentHashMap<String, String> links = new ConcurrentHashMap<>();
        links.put("SampleLink", "http://example.com");

        return new Event(
                fullName,
                venue,
                date,
                category,
                duration,
                fullDescription,
                ticketPrice,
                links
        );
    }
}
