package se.pbt.socialalert.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.pbt.socialalert.model.entity.Alert;
import se.pbt.socialalert.model.dto.AlertCreationDTO;
import se.pbt.socialalert.exception.AlertNotFoundException;
import se.pbt.socialalert.service.AlertService;

/**
 * Controller class handling asynchronous HTTP requests related to {@link Alert} entities.
 * This class defines endpoints for retrieving, adding, getting, and deleting alerts asynchronously.
 */
@Controller("/alerts")
public class AlertController {
    private final AlertService alertService;

    /**
     * Constructor for initializing the AlertController with the provided {@link AlertService} instance.
     *
     * @param alertService The AlertService instance used for business logic operations.
     */
    @Inject
    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }


    /**
     * Handles HTTP GET requests to retrieve all {@link Alert} objects asynchronously.
     *
     * @return A Flux emitting HTTP responses containing the list of alerts in JSON format.
     */
    @Get(produces = MediaType.APPLICATION_JSON)
    public Flux<MutableHttpResponse<Alert>> getAlerts() {
        return alertService.getAllAlerts()
                .map(HttpResponse::ok)
                .defaultIfEmpty(HttpResponse.notFound());
    }

    /**
     * Handles HTTP POST requests to add a new alert asynchronously.
     *
     * @param alertData The AlertCreationDTO object containing data for the new alert.
     * @return A Mono emitting an HTTP response indicating the status of the creation operation.
     */
    @Post(produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    public Mono<MutableHttpResponse<Alert>> addAlert(@Body AlertCreationDTO alertData) {
        return alertService.createAlert(alertData)
                .map(HttpResponse::created)
                .defaultIfEmpty(HttpResponse.badRequest());
    }

    /**
     * Handles HTTP GET requests to retrieve a specific alert by its ID asynchronously.
     *
     * @param id The unique identifier of the desired alert.
     * @return A Mono containing an HTTP response with the alert if found, or an error response otherwise.
     */
    @Get(uri = "/{id}", produces = MediaType.APPLICATION_JSON)
    public Mono<MutableHttpResponse<Alert>> getAlert(String id) {
        return alertService.getAlertById(id)
                .map(HttpResponse::ok)
                .defaultIfEmpty(HttpResponse.notFound());
    }

    /**
     * Handles HTTP DELETE requests to delete a specific alert by its ID asynchronously.
     *
     * @param id The unique identifier of the alert to be deleted.
     * @return A Mono emitting an HTTP response indicating the status of the deletion operation.
     */
    @Delete(uri = "/{id}", produces = MediaType.TEXT_PLAIN)
    public Mono<MutableHttpResponse<String>> deleteAlert(String id) {
        return alertService.deleteAlertById(id)
                .thenReturn(HttpResponse.ok("Alert deleted successfully"))
                .onErrorResume(AlertNotFoundException.class, ex -> Mono.just(HttpResponse.notFound()));
    }
}

