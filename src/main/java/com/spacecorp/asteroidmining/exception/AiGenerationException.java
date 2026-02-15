package com.spacecorp.asteroidmining.exception;

/**
 * Exception thrown when AI generation of new asteroids fail (e.g. malformed JSON, connection errors, token limit).
 */
public class AiGenerationException extends RuntimeException {
    public AiGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
