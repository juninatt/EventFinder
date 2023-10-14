package se.pbt.socialalert.testobject;

import org.bson.types.ObjectId;
import se.pbt.socialalert.model.dto.AlertCreationDTO;
import se.pbt.socialalert.model.entity.Alert;

/**
 * This class provides utility methods to create test objects for various classes
 * like {@link Alert} and {@link AlertCreationDTO}.
 * The purpose is to facilitate easy testing by generating sample data.
 */
public class TestObjectCreator {


    /**
     * Creates a sample {@link AlertCreationDTO} object with populated fields for testing purposes.
     *
     * @param trigger The keyword that is supposed to have triggered the alert.
     * @return A populated sample DTO object.
     */
    public static AlertCreationDTO alertCreationDTO(String trigger) {
        String triggerContext = "#" + trigger;
        String userAccount = "https://www.linkedin.com/in/sven/";
        String ipAddress = "192.168.0.1";
        String sourceReference = "https://www.linkedin.com/in/sven/post123427";
        String geographicLocation = "Gothenburg/Sweden";

        return new AlertCreationDTO(
                trigger,
                triggerContext,
                userAccount,
                ipAddress,
                sourceReference,
                geographicLocation
        );
    }

    /**
     * Creates a sample {@link Alert} object with pre-defined test values for testing purposes.
     *
     * @return A populated {@link Alert} object with test values.
     */
    public static Alert alert(String trigger) {
        String id = new ObjectId().toString();
        String triggerContext = "This burger is da bomb!";
        String userAccount = "https://www.linkedin.com/in/sven/";
        String ipAddress = "192.168.0.1";
        String sourceReference = "https://www.linkedin.com/in/sven/post123427";
        String geographicLocation = "Gothenburg/Sweden";

        return new Alert(
                id,
                trigger,
                triggerContext,
                userAccount,
                ipAddress,
                sourceReference,
                geographicLocation
        );
    }
}
