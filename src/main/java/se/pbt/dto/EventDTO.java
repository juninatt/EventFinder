package se.pbt.dto;

import io.micronaut.core.annotation.Introspected;
import se.pbt.domain.Event;

import java.time.Instant;
import java.util.concurrent.ConcurrentMap;

/**
 * Data Transfer Object (DTO) representation of an {@link Event}.
 * <p>
 * The {@code @Introspected} annotation marks this class for compile-time bean introspection.
 * </p>
 */
@Introspected
public record EventDTO(
        String name,
        String venue,
        Instant date,
        String category,
        Long duration,
        String description,
        Double ticketPrice,
        ConcurrentMap<String, String> links
) {}

