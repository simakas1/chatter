package lt.vaskevicius.chatter.controller;

import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import lt.vaskevicius.chatter.domain.exception.ChatterError;
import lt.vaskevicius.chatter.domain.exception.ChatterException;
import lt.vaskevicius.chatter.domain.exception.ChatterExceptionCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {ChatterException.class})
    public ResponseEntity<ChatterError> handleChatterException(HttpServletRequest request, ChatterException e) {
        ChatterError response = new ChatterError(e.getErrorCode(), e.getErrorMessage());

        ResponseEntity<ChatterError> responseEntity =
                new ResponseEntity<>(response, new HttpHeaders(), e.getHttpStatus() != null
                        ? e.getHttpStatus()
                        : HttpStatus.INTERNAL_SERVER_ERROR);

        StackTraceElement topStackFrame = (e.getStackTrace() != null && e.getStackTrace().length > 0)
                ? e.getStackTrace()[0]
                : null;

        if (responseEntity.getStatusCode().is5xxServerError()) {
            log.error("ChatterExceptionCode={}, clientErrorMessage={}, ChatterException.getHttpStatus()={}, topStackFrame={}",
                    e.getErrorCode(),
                    e.getMessage(),
                    e.getHttpStatus(),
                    topStackFrame);
        } else {
            log.info("ChatterExceptionCode={}, clientErrorMessage={}, ChatterException.getHttpStatus()={}, topStackFrame={}",
                    e.getErrorCode(),
                    e.getMessage(),
                    e.getHttpStatus(),
                    topStackFrame);
        }

        return responseEntity;
    }

    @ExceptionHandler(value = {AccessDeniedException.class, JwtException.class})
    public ResponseEntity<ChatterError> handleAccessException(HttpServletRequest request, Exception e) {

        ChatterException chatterException = new ChatterException( e instanceof JwtException ? ChatterExceptionCode.CHAT6 : ChatterExceptionCode.CHAT5);
        ChatterError response = new ChatterError(chatterException.getErrorCode(), e.getMessage());

        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ChatterError> handleValidationExceptions(MethodArgumentNotValidException ex) {

        var errorMessage = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = String.format("%s, field name: '%s'; ", error.getDefaultMessage(), ((FieldError) error).getField());
            errorMessage.append(fieldName);
        });
        ChatterError response = new ChatterError(ChatterExceptionCode.CHAT2.getCode(), errorMessage.toString());

        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ChatterError> handleException(HttpServletRequest request, Exception e) {

        ChatterException chatterException = new ChatterException(ChatterExceptionCode.CHAT1);
        ResponseEntity<ChatterError> responseEntity = new ResponseEntity<>(chatterException.getError(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

        StackTraceElement topStackFrame = (e.getStackTrace() != null && e.getStackTrace().length > 0)
                ? e.getStackTrace()[0]
                : null;

        log.error("Exception.getClass()={}, clientErrorMessage={}, Exception.getHttpStatus()={}, topStackFrame={}",
                e.getClass(),
                e.getMessage(),
                responseEntity.getStatusCode(),
                topStackFrame);

        log.info("exception", e);

        return responseEntity;
    }
}
