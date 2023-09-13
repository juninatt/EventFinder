package se.pbt.dto;

import io.micronaut.core.annotation.Introspected;

import java.time.Instant;
import java.util.Map;

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

