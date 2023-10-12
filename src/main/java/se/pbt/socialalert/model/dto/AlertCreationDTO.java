package se.pbt.socialalert.model.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import se.pbt.socialalert.model.entity.Alert;

/**
 * Data Transfer Object (DTO) representation of an {@link Alert}.
 * <p>
 * The {@code @Introspected} annotation marks this class for compile-time bean introspection.
 * </p>
 */

@Serdeable
@Introspected
public record AlertCreationDTO(
        @NotBlank String trigger,
        @NotNull Object triggerContext,
        @NotBlank String userAccount,
        @NotBlank String ipAddress,
        @NotBlank String sourceReference,
        @NotBlank String geographicLocation
) {}


