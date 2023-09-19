package se.pbt.unittest.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.pbt.domain.Event;
import se.pbt.repository.impl.MongoEventRepository;
import se.pbt.service.EventService;
import se.pbt.testobject.TestObjectCreator;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private MongoEventRepository mongoEventRepository;
    @InjectMocks
    private EventService eventService;

    @Test
    @DisplayName("Verify save")
    public void testSaveEvent() {
        Event event = TestObjectCreator.createSampleEvent();
        when(mongoEventRepository.save(event)).thenReturn(event);
        assertEquals(event, eventService.save(event));
    }

    @Test
    @DisplayName("Verify retrieve all objects")
    public void testFindAllEvents() {
        Event event1 = TestObjectCreator.createSampleEvent();
        Event event2 = TestObjectCreator.createSampleEvent();

        when(mongoEventRepository.findAll()).thenReturn(Arrays.asList(event1, event2));
        assertEquals(2, eventService.findAll().size());
        assertTrue(eventService.findAll().contains(event1));
        assertTrue(eventService.findAll().contains(event2));
    }

    @Test
    @DisplayName("Verify find by ID")
    public void testFindEventById_present() {
        Event event = TestObjectCreator.createSampleEvent();
        String id = "testId";

        when(mongoEventRepository.findById(id)).thenReturn(Optional.of(event));
        assertTrue(eventService.findById(id).isPresent());
        assertEquals(event, eventService.findById(id).get());
    }

    @Test
    @DisplayName("Verify delete by ID")
    public void testDeleteById_success() {
        String id = "testId";
        when(mongoEventRepository.deleteById(id)).thenReturn(true);
        assertTrue(eventService.deleteById(id));
    }
}
