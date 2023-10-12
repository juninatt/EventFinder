package se.pbt.socialalert.unittest.exception;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.pbt.socialalert.exception.*;
import se.pbt.socialalert.exception.handler.GlobalExceptionHandler;
import se.pbt.socialalert.exception.response.ErrorResponse;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("GlobalExceptionHandler Test: ")
public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;
    private HttpRequest<Object> mockRequest;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
        mockRequest = HttpRequest.GET("/");
    }

    @Test
    @DisplayName("Verify AlertNotFound is handled")
    void handleEventNotFoundException() {
        var exception = new AlertNotFoundException("13");

        HttpResponse<ErrorResponse> response = handler.handle(mockRequest, exception);

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND));

        assertThat("Title should be 'AlertNotFoundException'",
                response.getBody().get().getTitle(), is("AlertNotFoundException"));
        assertThat("Detail should be '13'",
                response.getBody().get().getDetail(), is("13"));
        assertThat("HTTP status code should be 404",
                response.getBody().get().getHttpStatusCode(), is(404));
    }


    @Test
    @DisplayName("Verify DataBaseConnectionException is handled")
    void handleDatabaseConnectionException() {
        var exception = new DatabaseConnectionException("DB Connection error", null);

        HttpResponse<ErrorResponse> response = handler.handle(mockRequest, exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        assertEquals("DatabaseConnectionException", response.getBody().get().getTitle());
        assertEquals("DB Connection error", response.getBody().get().getDetail());
        assertEquals(500, response.getBody().get().getHttpStatusCode());
    }

    @Test
    @DisplayName("Verify AlertSavingException is handled")
    void handleEventSavingException() {
        var exception = new AlertSavingException("Failed to save alert");

        HttpResponse<ErrorResponse> response = handler.handle(mockRequest, exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        assertEquals("AlertSavingException", response.getBody().get().getTitle());
        assertEquals("Failed to save alert", response.getBody().get().getDetail());
        assertEquals(500, response.getBody().get().getHttpStatusCode());
    }

    @Test
    @DisplayName("Verify AlertDeletionException is handled")
    void handleEvenDeletionException() {
        var exception = new AlertDeletionException("13");

        HttpResponse<ErrorResponse> response = handler.handle(mockRequest, exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        assertEquals("AlertDeletionException", response.getBody().get().getTitle());
        assertEquals("13", response.getBody().get().getDetail());
        assertEquals(500, response.getBody().get().getHttpStatusCode());
    }

    @Test
    @DisplayName("Verify AlertValidationException is handled")
    void handleEventValidationException() {
        var exception = new AlertValidationException("Could not validate alert");

        HttpResponse<ErrorResponse> response = handler.handle(mockRequest, exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertEquals("AlertValidationException", response.getBody().get().getTitle());
        assertEquals("Could not validate alert", response.getBody().get().getDetail());
        assertEquals(400, response.getBody().get().getHttpStatusCode());
    }

    @Test
    @DisplayName("Verify ConfigurationValidationException is handled")
    void handleConfigurationValidationException() {
        var exception = new ConfigurationValidationException("Configuration error");

        HttpResponse<ErrorResponse> response = handler.handle(mockRequest, exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        assertEquals("ConfigurationValidationException", response.getBody().get().getTitle());
        assertEquals("Configuration error", response.getBody().get().getDetail());
        assertEquals(500, response.getBody().get().getHttpStatusCode());
    }

    @Test
    @DisplayName("Verify DatabaseConnectionException is handled")
    void handleDatabaseConfigurationException() {
        var exception = new DatabaseConnectionException("Error while configuring database");

        HttpResponse<ErrorResponse> response = handler.handle(mockRequest, exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        assertEquals("DatabaseConnectionException", response.getBody().get().getTitle());
        assertEquals("Error while configuring database", response.getBody().get().getDetail());
        assertEquals(500, response.getBody().get().getHttpStatusCode());
    }

    @Test
    @DisplayName("Verify exception without custom annotation is handled")
    void handleGenericException() {
        var exception = new Exception("Undefined error");

        HttpResponse<ErrorResponse> response = handler.handle(mockRequest, exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        assertEquals("Exception", response.getBody().get().getTitle());
        assertEquals("Undefined error", response.getBody().get().getDetail());
        assertEquals(500, response.getBody().get().getHttpStatusCode());
    }
}
