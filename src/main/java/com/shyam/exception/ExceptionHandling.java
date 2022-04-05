package com.shyam.exception;

import com.shyam.model.HttpResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

/**
 * @author shyam jeganathan
 */

@RestControllerAdvice
public class ExceptionHandling implements ErrorController {
    public static final String ERROR_PATH = "/error";

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @ExceptionHandler(OpenApiAppNotFoundException.class)
    public ResponseEntity<HttpResponse> movieNotFoundException(OpenApiAppNotFoundException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(OpenApiAppFoundException.class)
    public ResponseEntity<HttpResponse> openApiAppFoundException(OpenApiAppFoundException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(InvalidOpenApiAppJsonException.class)
    public ResponseEntity<HttpResponse> invalidOpenApiAppJsonException(InvalidOpenApiAppJsonException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }
    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus,
                httpStatus.getReasonPhrase().toUpperCase(), message), httpStatus);
    }


}
