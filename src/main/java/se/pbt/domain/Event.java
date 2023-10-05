package se.pbt.domain;

import io.micronaut.core.annotation.Creator;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.validation.Validated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.bson.codecs.pojo.annotations.BsonId;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
@Validated
@Introspected
@Serdeable.Serializable
public class Event {
    @BsonId
    private String id;
    @NotBlank
    private final String name;
    @NotBlank
    private final String venue;
    @NotNull
    private final Instant date;
    @NotBlank
    private final String category;
    @Min(1)
    private final long duration;
    private final String description;
    @Min(0)
    private final double ticketPrice;
    private final Map<String, String> links;


    /**
     * Main constructor of the class. Injects data to all fields except 'id' which is generated when persisted to database.
     */
    @Creator
    public Event(
                 String name,
                 String venue,
                 Instant date,
                 String category,
                 long duration,
                 String description,
                 double ticketPrice,
                 Map<String, String> links
    )
    {
        this.name = name;
        this.venue = venue;
        this.date = date;
        this.category = category;
        this.duration = duration;
        this.description = description;
        this.ticketPrice = ticketPrice;
        this.links = new HashMap<>(links);
    }

    // Constructors for copying

    /**
     * Cloning constructor. Creates new event with updated links to maintain immutability.
     *
     * @param original The original event object.
     * @param links The new links for the event
     */
    public Event(Event original, Map<String, String> links)
    {
        this.name = original.getName();
        this.venue = original.getVenue();
        this.date = original.getDate();
        this.category = original.getCategory();
        this.duration = original.getDuration();
        this.description = original.getDescription();
        this.ticketPrice = original.getTicketPrice();
        this.links = new HashMap<>(links);
    }

    /**
     * Cloning constructor. Returns new event with given ID to maintain immutability.
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
        this.links = new HashMap<>(original.links);
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
