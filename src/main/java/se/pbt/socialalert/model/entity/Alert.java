package se.pbt.socialalert.model.entity;

import io.micronaut.core.annotation.Creator;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.bson.Document;
import org.bson.codecs.pojo.annotations.BsonId;

import java.time.Instant;
import java.util.Objects;

/**
 * Represents an alert with associated details.
 * <p>
 * This class provides an immutable representation of an alert, ensuring
 * thread safety and consistent state. Modifications result in a new object
 * being created.
 * </p>
 *
 * <p>
 * An alert object contains information such as its unique identifier, trigger,
 * trigger context, user account, IP address, source reference, geographic location,
 * and a timestamp indicating when the alert was created.
 * </p>
 */
@Introspected
@Serdeable.Serializable
public class Alert {
    @BsonId
    private final String id;

    /**
     * The keyword that triggers the alert.
     */
    @NotBlank
    private final String trigger;


    /**
     * The context in which the trigger was found.
     */
    @NotNull
    private final Object triggerContext;

    /**
     * The userAccount(if any) that posted the trigger.
     */
    private final String userAccount;

    /**
     * The IP address of the device the trigger was generated from.
     */
    @NotBlank
    private final String ipAddress;

    /**
     * An url or other reference to the source of the trigger.
     */
    @NotBlank
    private final String sourceReference;

    /**
     * The geographical location of the triggering device at time of the alert.
     */
    @NotBlank
    private final String geographicLocation;

    /**
     * Moment in time when alert was generated.
     */
    private final Instant timestamp;


    /**
     * Main constructor of the class used for object creation. Injects data to all fields except timestamp that is automatically initialized.
     */
    @Creator
    public Alert(String id,
                 String trigger,
                 Object triggerContext,
                 String userAccount,
                 String ipAddress,
                 String sourceReference,
                 String geographicLocation) {
        this.id = id;
        this.trigger = trigger;
        this.triggerContext = triggerContext;
        this.ipAddress = ipAddress;
        this.sourceReference = sourceReference;
        this.geographicLocation = geographicLocation;
        this.userAccount = userAccount;
        timestamp = Instant.now();
    }


    /**
     * Secondary constructor used for conversion from MongoDB {@link Document} when timestamp already exists.
     */
    public Alert(String id,
                 String trigger,
                 Object triggerContext,
                 String userAccount,
                 String ipAddress,
                 String sourceReference,
                 String geographicLocation,
                 Instant timestamp) {
        this.id = id;
        this.trigger = trigger;
        this.triggerContext = triggerContext;
        this.ipAddress = ipAddress;
        this.sourceReference = sourceReference;
        this.geographicLocation = geographicLocation;
        this.userAccount = userAccount;
        this.timestamp = timestamp;
    }

    // Getters

    public String getId() {
        return id;
    }

    public String getTrigger() {
        return trigger;
    }

    public Object getTriggerContext() {
        return triggerContext;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getSourceReference() {
        return sourceReference;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getGeographicLocation() {
        return geographicLocation;
    }

    // Overridden methods

    @Override
    public String toString() {
        return "Alert{" +
                "id='" + id + '\'' +
                ", trigger='" + trigger + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", timestamp=" + timestamp +
                ", location='" + geographicLocation + '\'' +
                ", userAccount='" + userAccount + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alert alert = (Alert) o;
        return Objects.equals(id, alert.id) &&
                Objects.equals(trigger, alert.trigger) &&
                Objects.equals(triggerContext, alert.triggerContext) &&
                Objects.equals(userAccount, alert.userAccount) &&
                Objects.equals(ipAddress, alert.ipAddress) &&
                Objects.equals(sourceReference, alert.sourceReference) &&
                Objects.equals(timestamp, alert.timestamp) &&
                Objects.equals(geographicLocation, alert.geographicLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, trigger, triggerContext, userAccount, ipAddress, sourceReference, timestamp, geographicLocation);
    }
}
