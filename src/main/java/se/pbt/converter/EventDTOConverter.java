package se.pbt.converter;

import se.pbt.domain.Event;
import se.pbt.dto.EventDTO;

import java.util.HashMap;

public class EventDTOConverter {

    /**
     * Converts an {@link Event} entity into an {@link EventDTO} record.
     *
     * @param event The event entity to be converted.
     * @return An EventDTO record representation of the given event.
     */
    public static EventDTO toDTO(Event event) {
        return new EventDTO(
                event.getName(),
                event.getVenue(),
                event.getDate(),
                event.getCategory(),
                event.getDuration(),
                event.getDescription(),
                event.getTicketPrice(),
                new HashMap<>(event.getLinks())
        );
    }

    /**
     * Converts an {@link EventDTO} record into an {@link Event} entity.
     *
     * @param dto The EventDTO record to be converted.
     * @return An Event entity representation of the given EventDTO.
     */
    public static Event fromDTO(EventDTO dto) {
        Event event = new Event();
        event.setName(dto.name());
        event.setVenue(dto.venue());
        event.setDate(dto.date());
        event.setCategory(dto.category());
        event.setDuration(dto.duration());
        event.setDescription(dto.description());
        event.setTicketPrice(dto.ticketPrice());
        event.setLinks(new HashMap<>(dto.links()));
        return event;
    }
}
