package se.pbt.unittest.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.pbt.controller.EventController;
import se.pbt.domain.Event;
import se.pbt.dto.EventDTO;
import se.pbt.exception.EventDeletionException;
import se.pbt.exception.EventNotFoundException;
import se.pbt.exception.EventValidationException;
import se.pbt.service.EventService;
import se.pbt.testobject.TestObjectCreator;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@MicronautTest
public class EventControllerTest {

    private EventController controller;
    private EventService mockEventService;

    @BeforeEach
    void setUp() {
        mockEventService = mock(EventService.class);
        controller = new EventController(mockEventService);
    }

    @Test
    @DisplayName("Verify list events retrieves all events correctly")
    void testListEvents() {
        Event sampleEvent = TestObjectCreator.createSampleEvent("Sample", "Description");
        when(mockEventService.findAll()).thenReturn(Collections.singletonList(sampleEvent));

        List<Event> result = controller.listEvents();

        assertEquals(1, result.size());
        assertEquals(sampleEvent, result.get(0));
    }

    @Test
    @DisplayName("Verify add event adds object correctly")
    void testAddEvent() {
        Event sampleEvent = TestObjectCreator.createSampleEvent("Sample", "Description");
        EventDTO sampleEventDTO = TestObjectCreator.createSampleEventDTO("Sample", "Description");
        when(mockEventService.save(any(Event.class))).thenReturn(sampleEvent);

        Event result = controller.addEvent(sampleEventDTO);

        assertEquals(sampleEvent, result);
    }

    @Test
    @DisplayName("Verify find by ID returns the event when present")
    void testGetByIdPresent() {
        Event sampleEvent = TestObjectCreator.createSampleEvent("Sample", "Description");
        when(mockEventService.findById("1")).thenReturn(Optional.of(sampleEvent));

        HttpResponse<Event> response = controller.getById("1");

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(sampleEvent, response.getBody().get());
    }

    @Test
    @DisplayName("Verify get event by ID returns not found when absent")
    void testGetByIdAbsent() {
        when(mockEventService.findById("2")).thenReturn(Optional.empty());

        HttpResponse<Event> response = controller.getById("2");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
    }



    @Test
    @DisplayName("Verify get event by ID throws EventNotFoundException when no object is found")
    void testGetByIdThrowsEventNotFoundException() {
        when(mockEventService.findById("5")).thenThrow(new EventNotFoundException("5"));

        assertThrows(EventNotFoundException.class, () -> controller.getById("5"));

        EventNotFoundException exception = assertThrows(EventNotFoundException.class, () -> controller.getById("5"));

        assertEquals("No event found with ID: 5", exception.getMessage());
    }

    @Test
    @DisplayName("Verify add event throws InvalidEventException when name and description are null")
    void testAddEventThrowsInvalidEventException() {
        EventDTO invalidEventDTO = TestObjectCreator.createSampleEventDTO(null, null);
        when(mockEventService.save(any(Event.class))).thenThrow(new EventValidationException("Invalid Event"));

        EventValidationException exception = assertThrows(EventValidationException.class, () -> controller.addEvent(invalidEventDTO));

        assertEquals("Invalid Event", exception.getMessage());
    }

    @Test
    @DisplayName("Verify delete event by ID throws EventDeletionException")
    void testDeleteByIdThrowsEventDeletionException() {
        when(mockEventService.deleteById("5")).thenThrow(new EventDeletionException("5"));

        EventDeletionException exception = assertThrows(EventDeletionException.class, () -> controller.deleteById("5"));

        assertEquals("Error deleting event with ID: 5", exception.getMessage());
    }

}

