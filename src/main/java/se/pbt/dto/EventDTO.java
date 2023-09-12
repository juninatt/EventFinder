package se.pbt.dto;

import java.time.Instant;
import java.util.Map;

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

