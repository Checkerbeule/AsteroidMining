package com.spacecorp.asteroidmining.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

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

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Helper method to standardize the error response format.
     *
     * @param status     the HTTP status to return.
     * @param message    the descriptive error message.
     * @param trackingId a unique identifier for error tracing (can be null).
     * @return a {@link ResponseEntity} containing timestamp, status code, error reason phrase and message.
     */
    private ResponseEntity<Object> buildResponse(HttpStatus status, String message, String trackingId) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        if (trackingId != null) body.put("trackingId", trackingId);

        return new ResponseEntity<>(body, status);
    }

    private ResponseEntity<Object> buildResponse(HttpStatus status, String message) {
        return buildResponse(status, message, null);
    }

    /**
     * PHelper method to handle critical exceptions by generating a unique tracking ID,
     * logging the full stack trace for debugging, and returning a
     * standardized error response.
     *
     * @param httpStatus  the HTTP status code to be returned to the client.
     * @param userMessage the user-friendly message to be included in the response.
     * @param ex          the caught exception containing the technical details to be logged.
     * @return a {@link ResponseEntity} containing the error details and the tracking ID.
     */
    private ResponseEntity<Object> handleErrorWithTrackingId(HttpStatus httpStatus, String userMessage, Exception ex) {
        String trackingId = UUID.randomUUID().toString();
        logger.error("{} [Tracking-ID: {}]: ", httpStatus.getReasonPhrase(), trackingId, ex);

        String messageWithId = String.format("%s (Ref: %s)", userMessage, trackingId);
        return buildResponse(httpStatus, messageWithId, trackingId);
    }

    /**
     * Handles all unexpected internal server errors.
     *
     * @param ex the caught exception.
     * @return a {@link ResponseEntity} with status 500 and error details.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        logger.error("An unexpected error occurred: ", ex);
        return handleErrorWithTrackingId(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An internal server error occurred. Please try again later.",
                ex);
    }

    /**
     * Specifically handles cases where a requested asteroid is missing.
     *
     * @param ex the caught {@link AsteroidNotFoundException}.
     * @return a {@link ResponseEntity} with status 404 and a specific message.
     */
    @ExceptionHandler(AsteroidNotFoundException.class)
    public ResponseEntity<Object> handleAsteroidNotFound(AsteroidNotFoundException ex) {
        logger.info("Asteroid not found: {}", ex.getMessage());
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    /**
     * Handles errors occurring while asteroid discovery. This can happen when AI generation of asteroids fail.
     *
     * @param ex the caught {@link AsteroidDiscoveryException}.
     * @return a {@link ResponseEntity} with status 503 and a specific message.
     */
    @ExceptionHandler(AsteroidDiscoveryException.class)
    public ResponseEntity<Object> handleDiscoveryException(AsteroidDiscoveryException ex) {
        logger.error("Asteroid discovery failed: ", ex);
        return handleErrorWithTrackingId(
                HttpStatus.SERVICE_UNAVAILABLE,
                ex.getMessage(),
                ex);
    }
}
