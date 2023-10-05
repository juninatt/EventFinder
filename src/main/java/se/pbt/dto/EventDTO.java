package se.pbt.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
        @NotNull String name,
        @NotNull String venue,
        @NotNull Instant date,
        String category,
        @Min(1) Long duration,
        String description,
        @Min(0) Double ticketPrice,
        Map<String, String> links
) {}


