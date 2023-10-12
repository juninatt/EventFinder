package se.pbt.socialalert.converter;

import jakarta.validation.constraints.NotNull;
import org.bson.Document;
import org.bson.types.ObjectId;
import se.pbt.socialalert.model.entity.Alert;
import se.pbt.socialalert.model.dto.AlertCreationDTO;
import se.pbt.socialalert.exception.AlertConversionException;

import java.time.Instant;
import java.time.format.DateTimeParseException;

/**
 * Utility class that provides methods to convert between {@link Alert}-, MongoDB {@link Document}-,
 * and {@link AlertCreationDTO} objects.
 */
public class AlertConverter {


    /**
     * Converts an {@link AlertCreationDTO} record into an {@link Alert} entity.
     *
     * @param dto The DTO record to be converted.
     * @return An entity representation of the given DTO.
     */
    public static Alert toEntity(@NotNull AlertCreationDTO dto) {
        return new Alert(
                null,
                dto.trigger(),
                dto.triggerContext(),
                dto.userAccount(),
                dto.ipAddress(),
                dto.sourceReference(),
                dto.geographicLocation()
        );
    }

    /**
     * Converts a MongoDB {@link Document} into an {@link Alert} object.
     *
     * @param document The document to be converted.
     * @return An object entity representation of the given document with all its fields populated.
     */
    public static Alert toEntity(@NotNull Document document) {
        return new Alert(
                document.getObjectId("_id").toString(),
                document.getString("trigger"),
                document.getString("triggerContext"),
                document.getString("userAccount"),
                document.getString("ipAddress"),
                document.getString("sourceReference"),
                document.getString("geographicLocation"),
                parseInstant(document.getString("timestamp")
        ));
    }

    /**
     * Converts an {@link AlertCreationDTO} object into an {@link Alert} object with a specified ID.
     *
     * @param id  The ID for the Event object.
     * @param dto The EventDTO object containing updated information.
     * @return An object entity representation of the given DTO with all its fields populated and the specified ID.
     */
    public static Alert toEntity(String id, @NotNull AlertCreationDTO dto) {
        return new Alert(
                id,
                dto.trigger(),
                dto.triggerContext(),
                dto.userAccount(),
                dto.ipAddress(),
                dto.sourceReference(),
                dto.geographicLocation()
        );
    }

    /**
     * Converts an {@link Alert} object into a MongoDB {@link Document}.
     *
     * @param alert The entity object to be converted.
     * @return The document representation of the given entity.
     */
    public static Document toDocument(@NotNull Alert alert) {
        Document document = new Document();
        if (alert.getId() == null)
            document.put("_id", new ObjectId());
        else {
            try {
                document.put("_id", new ObjectId(alert.getId()));
            } catch (IllegalArgumentException exception) {
                throw new AlertConversionException(exception.getMessage(), exception.getCause());
            }
        }
        document.put("trigger", alert.getTrigger());
        document.put("triggerContext", alert.getTriggerContext());
        document.put("userAccount", alert.getUserAccount());
        document.put("ipAddress", alert.getIpAddress());
        document.put("sourceReference", alert.getSourceReference());
        document.put("geographicLocation", alert.getGeographicLocation());
        document.put("timestamp", alert.getTimestamp().toString());
        return document;
    }


    /**
     * Safely parses a given string representation of a date-time into an {@link Instant}.
     *
     * @param dateString The string representation of the date-time to be parsed.
     * @return An Instant representing the parsed date-time.
     * @throws DateTimeParseException if the string cannot be parsed to an Instant.
     */
    private static Instant parseInstant(String dateString) {
        try {
            return Instant.parse(dateString);
        } catch (DateTimeParseException exception) {
                throw new DateTimeParseException(exception.getMessage(), exception.getParsedString(), exception.getErrorIndex());
        }
    }
}
