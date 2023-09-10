package se.pbt.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.pbt.domain.Event;
import se.pbt.repository.impl.MongoEventRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private MongoEventRepository mongoEventRepository;
    @InjectMocks
    private EventService eventService;

    @Test
    public void testSaveEvent() {
        Event event = new Event();
        when(mongoEventRepository.save(event)).thenReturn(event);
        assertEquals(event, eventService.save(event));
    }

    @Test
    public void testFindAllEvents() {
        Event event1 = new Event();
        Event event2 = new Event();

        when(mongoEventRepository.findAll()).thenReturn(Arrays.asList(event1, event2));
        assertEquals(2, eventService.findAll().size());
        assertTrue(eventService.findAll().contains(event1));
        assertTrue(eventService.findAll().contains(event2));
    }

    @Test
    public void testFindEventById_present() {
        Event event = new Event();
        String id = "testId";

        when(mongoEventRepository.findById(id)).thenReturn(Optional.of(event));
        assertTrue(eventService.findById(id).isPresent());
        assertEquals(event, eventService.findById(id).get());
    }

    @Test
    public void testFindEventById_notPresent() {
        String id = "testId";
        when(mongoEventRepository.findById(id)).thenReturn(Optional.empty());
        assertFalse(eventService.findById(id).isPresent());
    }

    @Test
    public void testDeleteById_success() {
        String id = "testId";
        when(mongoEventRepository.deleteById(id)).thenReturn(true);
        assertTrue(eventService.deleteById(id));
    }

    @Test
    public void testDeleteById_failure() {
        String id = "testId";
        when(mongoEventRepository.deleteById(id)).thenReturn(false);
        assertFalse(eventService.deleteById(id));
    }
}
