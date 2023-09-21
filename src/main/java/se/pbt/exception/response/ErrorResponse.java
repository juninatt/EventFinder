package se.pbt.exception.response;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable.Serializable
public class ErrorResponse {
    private final int httpStatusCode;
    private final String internalCode;
    private final String title;
    private final String detail;

    public ErrorResponse(int httpStatusCode, String internalCode, String title, String detail) {
        this.httpStatusCode = httpStatusCode;
        this.internalCode = internalCode;
        this.title = title;
        this.detail = detail;
    }

    // Getters and setters ...

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getInternalCode() {
        return internalCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }
}
