package se.pbt.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import se.pbt.converter.EventDTOConverter;
import se.pbt.domain.Event;
import se.pbt.dto.EventDTO;
import se.pbt.service.EventService;

import java.util.List;
import java.util.Optional;

/**
 * Controller for handling HTTP requests related to {@link Event} objects.
 * Provides endpoints for listing, adding, retrieving, and deleting events.
 */
@Controller("/events")
public class EventController {
    private final EventService eventService;

    @Inject
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Retrieves a list of all events.
     *
     * @return A list of {@link Event} objects.
     */
    @Get
    public List<Event> listEvents() {
        return eventService.findAll();
    }

    /**
     * Adds a new event.
     *
     * @param event The event to be added.
     * @return The added {@link Event} object.
     */
    @Post
    public Event addEvent(EventDTO event) {
        return eventService.save(EventDTOConverter.fromDTO(event));
    }

    /**
     * Retrieves an event by its ID.
     *
     * @param id The ID of the event to be retrieved.
     * @return An HTTP response containing the event or an HTTP not found response.
     */
    @Get("/{id}")
    public HttpResponse<Event> getById(String id) {
        Optional<Event> optionalEvent = eventService.findById(id);
        if (optionalEvent.isPresent()) {
            return HttpResponse.ok(optionalEvent.get());
        } else {
            return HttpResponse.notFound();
        }
    }

    /**
     * Deletes an event by its ID.
     *
     * @param id The ID of the event to be deleted.
     * @return An HTTP response indicating the successful deletion.
     */
    @Delete("/{id}")
    public HttpResponse<String> deleteById(String id) {
        eventService.deleteById(id);
        return HttpResponse.ok("Event deleted successfully");
    }
}

