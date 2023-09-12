package se.pbt.unittest.exception;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.pbt.exception.DatabaseConnectionException;
import se.pbt.exception.EventNotFoundException;
import se.pbt.exception.EventSavingException;
import se.pbt.exception.handler.GlobalExceptionHandler;
import se.pbt.exception.response.ErrorResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;
    private HttpRequest<Object> mockRequest;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
        mockRequest = HttpRequest.GET("/");
    }

    @Test
    @DisplayName("Verify EventNotFound is handled")
    void handleEventNotFoundException() {
        EventNotFoundException exception = new EventNotFoundException("13");

        HttpResponse<ErrorResponse> response = handler.handle(mockRequest, exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        assertEquals("EventNotFoundException", response.getBody().get().getTitle());
        assertEquals("No event found with ID: 13", response.getBody().get().getDetail());
        assertEquals(404, response.getBody().get().getHttpStatusCode());
    }

    @Test
    @DisplayName("Verify DataBaseConnectionException is handled")
    void handleDatabaseConnectionException() {
        DatabaseConnectionException exception = new DatabaseConnectionException("DB Connection error", null);

        HttpResponse<ErrorResponse> response = handler.handle(mockRequest, exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        assertEquals("DatabaseConnectionException", response.getBody().get().getTitle());
        assertEquals("DB Connection error", response.getBody().get().getDetail());
        assertEquals(500, response.getBody().get().getHttpStatusCode());
    }

    @Test
    @DisplayName("Verify EventSavingException is handled")
    void handleEventSavingException() {
        EventSavingException exception = new EventSavingException("Failed to save event");

        HttpResponse<ErrorResponse> response = handler.handle(mockRequest, exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        assertEquals("EventSavingException", response.getBody().get().getTitle());
        assertEquals("Failed to save event", response.getBody().get().getDetail());
        assertEquals(500, response.getBody().get().getHttpStatusCode());

    }

    @Test
    @DisplayName("Verify DataBaseConnectionException is handled")
    void handleGenericException() {
        Exception exception = new Exception("Some generic error");

        HttpResponse<ErrorResponse> response = handler.handle(mockRequest, exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        assertEquals("Exception", response.getBody().get().getTitle());
        assertEquals("Some generic error", response.getBody().get().getDetail());
        assertEquals(500, response.getBody().get().getHttpStatusCode());
    }
}
