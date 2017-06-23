package za.co.dladle.exception;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by prady on 7/25/2016.
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ConversionFailedException.class, NumberFormatException.class})
    protected ResponseEntity<Object> handleConflictException(RuntimeException re, WebRequest wr) {
        String bodyOfResponse = "Please provide correct parameters";
        return handleExceptionInternal(re, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, wr);
    }
}
