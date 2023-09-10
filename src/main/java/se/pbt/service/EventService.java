package se.pbt.service;

import jakarta.inject.Singleton;
import se.pbt.domain.Event;
import se.pbt.repository.impl.MongoEventRepository;

import java.util.List;
import java.util.Optional;

@Singleton
public class EventService {

    private final MongoEventRepository repository;

    public EventService(MongoEventRepository repository) {
        this.repository = repository;
    }

    public Event save(Event event) {
        return repository.save(event);
    }

    public List<Event> findAll() {
        return repository.findAll();
    }

    public Optional<Event> findById(String id) {
        return repository.findById(id);
    }

    public boolean deleteById(String id) {
        return repository.deleteById(id);
    }
}
