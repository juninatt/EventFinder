package se.pbt.exception.handler;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;
import se.pbt.annotation.HttpStatusAnnotation;
import se.pbt.exception.response.ErrorResponse;

/**
 * Handles global exceptions, providing HTTP responses based on {@link HttpStatusAnnotation}.
 * Defaults to INTERNAL_SERVER_ERROR if the annotation is absent.
 */
@Singleton
@Replaces(ExceptionHandler.class)
public class GlobalExceptionHandler implements ExceptionHandler<Exception, HttpResponse<?>> {

    /**
     * Handles the exception and returns the corresponding response.
     *
     * @param request   The incoming request.
     * @param exception The captured exception.
     * @return A structured error response.
     */
    @Override
    public HttpResponse<ErrorResponse> handle(HttpRequest request, Exception exception) {
        return handleException(exception);
    }

    /**
     * Maps the exception to an {@link ErrorResponse} using its {@link HttpStatusAnnotation}.
     *
     * @param exception The exception for mapping.
     * @return The generated error response.
     */
    private HttpResponse<ErrorResponse> handleException(Exception exception) {
        HttpStatusAnnotation annotation = exception.getClass().getAnnotation(HttpStatusAnnotation.class);
        HttpStatus exceptionHttpStatus = (annotation != null) ? annotation.value() : HttpStatus.INTERNAL_SERVER_ERROR;

        return HttpResponse
                .status(exceptionHttpStatus)
                .body(new ErrorResponse(exceptionHttpStatus.getCode(), null, exception.getClass().getSimpleName(), exception.getMessage()));

    }
}
