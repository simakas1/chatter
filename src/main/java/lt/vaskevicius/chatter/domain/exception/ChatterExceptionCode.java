package lt.vaskevicius.chatter.domain.exception;

import org.springframework.http.HttpStatus;

public enum ChatterExceptionCode {

    CHAT1("CHAT1", "Service unavailable", HttpStatus.SERVICE_UNAVAILABLE),
    CHAT2("CHAT2", "Invalid data", HttpStatus.BAD_REQUEST),
    CHAT3("CHAT3", "Insufficient access rights", HttpStatus.BAD_REQUEST),

    CHAT4("CHAT4", "Not found", HttpStatus.NOT_FOUND),
    CHAT5("CHAT5", "Failed to Authorize", HttpStatus.UNAUTHORIZED),
    CHAT6("CHAT6", "Invalid authorization token", HttpStatus.UNAUTHORIZED);


    private String code;
    private String message;
    private HttpStatus httpStatus;

    ChatterExceptionCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
