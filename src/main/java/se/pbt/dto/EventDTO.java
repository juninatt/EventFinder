package se.pbt.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import se.pbt.domain.Event;

import java.time.Instant;
import java.util.Map;

/**
 * Data Transfer Object (DTO) representation of an {@link Event}.
 * <p>
 * The {@code @Introspected} annotation marks this class for compile-time bean introspection.
 * </p>
 */
@Serdeable
@Introspected
public record EventDTO(
        String name,
        String venue,
        Instant date,
        String category,
        Long duration,
        String description,
        Double ticketPrice,
        Map<String, String> links
) {}

