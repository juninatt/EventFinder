package se.pbt.domain;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import org.bson.codecs.pojo.annotations.BsonId;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Represents an event with associated details.
 * <p>
 * This class provides an immutable representation of an event, ensuring
 * thread safety and consistent state. Modifications result in a new object
 * being created.
 * </p>
 * <p>
 * The {@code @Introspected} annotation marks this class for compile-time bean introspection.
 * </p>
 */
@Introspected
@Serdeable.Serializable
public final class Event {

    @BsonId
    private String id;
    private final String name;

    private final String venue;

    private final Instant date;

    private final String category;

    private final long duration;
    private final String description;

    private final double ticketPrice;

    /**
     * Map of event links, implemented as a ConcurrentMap to safeguard against
     * any concurrent modifications that might be introduced in future versions
     * of this class.
     */
    private final ConcurrentMap<String, String> links;



    /**
     * Main constructor of the class. Injects data to all fields except 'id'.
     */
    public Event(
                 String name,
                 String venue,
                 Instant date,
                 String category,
                 long duration,
                 String description,
                 double ticketPrice,
                 ConcurrentMap<String, String> links
    )
    {
        this.name = name;
        this.venue = venue;
        this.date = date;
        this.category = category;
        this.duration = duration;
        this.description = description;
        this.ticketPrice = ticketPrice;
        this.links = new ConcurrentHashMap<>(links);
    }

    // Constructors for copying

    /**
     * Cloning constructor. Creates new event with updated links.
     *
     * @param original The original event object.
     * @param links The new links for the event
     */
    public Event(Event original, ConcurrentMap<String, String> links)
    {
        this.name = original.getName();
        this.venue = original.getVenue();
        this.date = original.getDate();
        this.category = original.getCategory();
        this.duration = original.getDuration();
        this.description = original.getDescription();
        this.ticketPrice = original.getTicketPrice();
        this.links = new ConcurrentHashMap<>(links);
    }

    /**
     * Cloning constructor. Creates new event with ID from input.
     *
     * @param original The original event object.
     * @param id The ID to be associated with the event.
     */
    public Event(Event original, String id) {
        this.id = id;
        this.name = original.name;
        this.venue = original.venue;
        this.date = original.date;
        this.category = original.category;
        this.duration = original.duration;
        this.description = original.description;
        this.ticketPrice = original.ticketPrice;
        this.links = new ConcurrentHashMap<>(original.links);
    }

    // Getters

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getVenue() {
        return venue;
    }

    public Instant getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public long getDuration() {
        return duration;
    }

    public String getDescription() {
        return description;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public Map<String, String> getLinks() {
        return Collections.unmodifiableMap(links);
    }

    // Overridden methods

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", venue='" + venue + '\'' +
                ", date=" + date +
                ", links=" + links +
                ", duration=" + duration +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return duration == event.duration &&
                Objects.equals(id, event.id) &&
                Objects.equals(name, event.name) &&
                Objects.equals(venue, event.venue) &&
                Objects.equals(date, event.date) &&
                Objects.equals(links, event.links) &&
                Objects.equals(description, event.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, venue, date, links, duration, description);
    }
}
