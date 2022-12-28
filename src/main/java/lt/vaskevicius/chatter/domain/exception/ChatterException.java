package lt.vaskevicius.chatter.domain.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper=false)
public class ChatterException extends RuntimeException {

    private String errorCode;
    private String errorMessage;
    private HttpStatus httpStatus;

    public ChatterError getError() {
        return new ChatterError(errorCode, errorMessage);
    }

    public ChatterException(ChatterExceptionCode chatterExceptionCode) {
        super(chatterExceptionCode.getMessage());
        this.errorCode = chatterExceptionCode.getCode();
        this.errorMessage = chatterExceptionCode.getMessage();
        this.httpStatus = chatterExceptionCode.getHttpStatus();
    }

    public ChatterException(ChatterExceptionCode chatterExceptionCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = chatterExceptionCode.getCode();
        this.errorMessage = errorMessage;
        this.httpStatus = chatterExceptionCode.getHttpStatus();
    }
}
