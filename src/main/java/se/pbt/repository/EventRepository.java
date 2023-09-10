package se.pbt.repository;

import se.pbt.domain.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepository {
    Event save(Event event);
    Optional<Event> findById(String id);
    List<Event> findAll();
    boolean deleteById(String id);
}
