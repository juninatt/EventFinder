package se.pbt.dataloader;

import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import jakarta.inject.Singleton;
import se.pbt.domain.Event;
import se.pbt.repository.EventRepository;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class DataLoader implements ApplicationEventListener<ServerStartupEvent> {

    private final EventRepository eventRepository;

    public DataLoader(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void onApplicationEvent(ServerStartupEvent event) {
        eventRepository.clearAll();
        List<Event> events = Arrays.asList(
                new Event(
                        "Music Festival 2023",
                        "Arena Göteborg",
                        Instant.parse("2023-09-25T14:00:00Z"),
                        "Music",
                        300,
                        "A grand music festival featuring international artists.",
                        200.0,
                        new ConcurrentHashMap<>() {{
                            put("website", "http://musicfestival2023.com");
                            put("booking", "http://booking1.com");
                        }}
                ),
                new Event(
                        "Tech Conference 2023",
                        "Göteborg Convention Center",
                        Instant.parse("2023-10-05T09:00:00Z"),
                        "Tech",
                        480,
                        "The premier tech conference for developers and innovators.",
                        150.0,
                        new ConcurrentHashMap<>() {{
                            put("website", "http://techconference2023.com");
                            put("booking", "http://booking2.com");
                        }}
                ),
                new Event(
                        "Art Exhibition: Modern Masters",
                        "Göteborg Art Museum",
                        Instant.parse("2023-11-15T11:00:00Z"),
                        "Art",
                        240,
                        "Showcasing works from the leading artists of our time.",
                        50.0,
                        new ConcurrentHashMap<>() {{
                            put("website", "http://artexhibition2023.com");
                            put("booking", "http://booking3.com");
                        }}
                ),
                new Event(
                        "Food & Wine Festival",
                        "Central Göteborg Park",
                        Instant.parse("2023-12-01T12:00:00Z"),
                        "Food",
                        420,
                        "Taste dishes and wines from the world's finest chefs and vineyards.",
                        100.0,
                        new ConcurrentHashMap<>() {{
                            put("website", "http://foodwinefestival.com");
                            put("booking", "http://booking4.com");
                        }}
                ),
                new Event(
                        "Theatre: A Midsummer Night's Dream",
                        "Göteborg Theatre",
                        Instant.parse("2023-12-20T19:00:00Z"),
                        "Theatre",
                        150,
                        "A classic Shakespearean play brought to life by renowned actors.",
                        75.0,
                        new ConcurrentHashMap<>() {{
                            put("website", "http://theatre2023.com");
                            put("booking", "http://booking5.com");
                        }}
                ),
                new Event(
                        "Jazz Night 2023",
                        "Jazz Club Göteborg",
                        Instant.parse("2024-01-15T20:00:00Z"),
                        "Music",
                        180,
                        "Experience the soulful tunes of jazz with leading musicians.",
                        60.0,
                        new ConcurrentHashMap<>() {{
                            put("website", "http://jazznight2023.com");
                            put("booking", "http://booking6.com");
                        }}
                ),
                new Event(
                        "Film Festival 2024",
                        "Cinema Göteborg",
                        Instant.parse("2024-02-10T15:00:00Z"),
                        "Film",
                        360,
                        "A collection of groundbreaking films from acclaimed filmmakers.",
                        40.0,
                        new ConcurrentHashMap<>() {{
                            put("website", "http://filmfestival2024.com");
                            put("booking", "http://booking7.com");
                        }}
                )
        );
        events.forEach(eventRepository::save);
    }
}


