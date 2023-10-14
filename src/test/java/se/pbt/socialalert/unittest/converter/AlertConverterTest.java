package se.pbt.socialalert.unittest.converter;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.pbt.socialalert.converter.AlertConverter;
import se.pbt.socialalert.model.entity.Alert;
import se.pbt.socialalert.testobject.TestObjectCreator;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("AlertConverter:")
public class AlertConverterTest {

    @Test
    @DisplayName("Verify conversion from alert to document")
    public void convertAlertEntity_toDocument() {
        Alert testAlert = TestObjectCreator.alert("Justin Bieber Concert");

        Document doc = AlertConverter.toDocument(testAlert);

        assertEquals(testAlert.getTrigger(), doc.getString("trigger"));
        assertEquals(testAlert.getTriggerContext(), doc.getString("triggerContext"));
        assertEquals(testAlert.getUserAccount(), doc.getString("userAccount"));
        assertEquals(testAlert.getIpAddress(), doc.getString("ipAddress"));
        assertEquals(testAlert.getSourceReference(), doc.getString("sourceReference"));
        assertEquals(testAlert.getGeographicLocation(), doc.getString("geographicLocation"));
    }

    @Test
    @DisplayName("Verify conversion from document to alert")
    public void convertDocument_toAlertEntity() {
        Document doc = new Document();
        doc.put("_id", new ObjectId());
        doc.put("trigger", "cleansing");
        doc.put("triggerContext", "That yoga retreat was really cleansing bro");
        doc.put("userAccount", "https://sv-se.facebook.com/carlejnar");
        doc.put("ipAddress", "192.168.0.1.45");
        doc.put("sourceReference", "https://sv-se.facebook.com/carlejnar/comments/j73y42");
        doc.put("geographicLocation", "London/England");
        doc.put("timestamp", Instant.now().toString());

        Alert convertedAlert = AlertConverter.toEntity(doc);

        assertEquals("cleansing", convertedAlert.getTrigger());
        assertEquals("That yoga retreat was really cleansing bro", convertedAlert.getTriggerContext());
        assertEquals("https://sv-se.facebook.com/carlejnar", convertedAlert.getUserAccount());
        assertEquals("192.168.0.1.45", convertedAlert.getIpAddress());
        assertEquals("https://sv-se.facebook.com/carlejnar/comments/j73y42", convertedAlert.getSourceReference());
        assertEquals("London/England", convertedAlert.getGeographicLocation());


    }
}
