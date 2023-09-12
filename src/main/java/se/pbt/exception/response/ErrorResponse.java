package se.pbt.exception.response;

public class ErrorResponse {
    private int httpStatusCode;
    private String internalCode;
    private String title;
    private String detail;

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

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getInternalCode() {
        return internalCode;
    }

    public void setInternalCode(String internalCode) {
        this.internalCode = internalCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
