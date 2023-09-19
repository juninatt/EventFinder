package se.pbt.converter;

import se.pbt.domain.Event;
import se.pbt.dto.EventDTO;

import java.util.concurrent.ConcurrentHashMap;

public class EventDTOConverter {

    /**
     * Converts an {@link EventDTO} record into an {@link Event} entity.
     *
     * @param dto The EventDTO record to be converted.
     * @return An Event entity representation of the given EventDTO.
     */
    public static Event fromDTO(EventDTO dto) {
        return new Event(
                dto.name(),
                dto.venue(),
                dto.date(),
                dto.category(),
                dto.duration(),
                dto.description(),
                dto.ticketPrice(),
                new ConcurrentHashMap<>(dto.links())
        );
    }
}
