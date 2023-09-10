package se.pbt.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import se.pbt.domain.Event;
import se.pbt.service.EventService;

import java.util.List;
import java.util.Optional;

@Controller("/events")
public class EventController {

    @Inject
    private EventService eventService;

    @Get
    public List<Event> listEvents() {
        return eventService.findAll();
    }

    @Post
    public Event addEvent(Event event) {
        return eventService.save(event);
    }

    @Get("/{id}")
    public HttpResponse<Event> getById(String id) {
        Optional<Event> optionalEvent = eventService.findById(id);
        if (optionalEvent.isPresent()) {
            return HttpResponse.ok(optionalEvent.get());
        } else {
            return HttpResponse.notFound();
        }
    }

    @Delete("/{id}")
    public HttpResponse<String> deleteById(String id) {
        eventService.deleteById(id);
        return HttpResponse.ok("Event deleted successfully");
    }
}

