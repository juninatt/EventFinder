package se.pbt.exception.handler;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;
import se.pbt.exception.DatabaseConnectionException;
import se.pbt.exception.EventNotFoundException;
import se.pbt.exception.EventSavingException;
import se.pbt.exception.response.ErrorResponse;

@Singleton
@Replaces(ExceptionHandler.class)
public class GlobalExceptionHandler implements ExceptionHandler<Exception, HttpResponse<?>> {

    @Override
    public HttpResponse<ErrorResponse> handle(HttpRequest request, Exception exception) {
        if (exception instanceof EventNotFoundException) {
            return handleException(exception, HttpStatus.NOT_FOUND);
        } else if (exception instanceof DatabaseConnectionException) {
            return handleException(exception, HttpStatus.INTERNAL_SERVER_ERROR);
        } else if (exception instanceof EventSavingException) {
            return handleException(exception, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return handleException(exception, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private HttpResponse<ErrorResponse> handleException(Exception exception, HttpStatus httpStatus) {
        return HttpResponse
                .status(httpStatus)
                .body(new ErrorResponse(httpStatus.getCode(), null, exception.getClass().getSimpleName(), exception.getMessage()));

    }
}
