package com.spacecorp.asteroidmining.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Global interceptor for handling exceptions across all REST controllers.
 * <p>
 * This class uses the {@link ControllerAdvice} annotation to provide centralized
 * error handling. It ensures that the API consistently returns structured JSON
 * responses instead of default HTML error pages.
 * </p>
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Helper method to standardize the error response format.
     * @param status the HTTP status to return.
     * @param message the descriptive error message.
     * @return a {@link ResponseEntity} containing timestamp, status code, error reason phrase and message.
     */
    private ResponseEntity<Object> buildResponse(HttpStatus status, String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);

        return new ResponseEntity<>(body, status);
    }

    /**
     * Handles all unexpected internal server errors.
     * @param ex the caught exception.
     * @return a {@link ResponseEntity} with status 500 and error details.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    /**
     * Specifically handles cases where a requested asteroid is missing.
     * @param ex the caught {@link AsteroidNotFoundException}.
     * @return a {@link ResponseEntity} with status 404 and a specific message.
     */
    @ExceptionHandler(AsteroidNotFoundException.class)
    public ResponseEntity<Object> handleAsteroidNotFound(AsteroidNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }
}
