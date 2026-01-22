package com.spacecorp.asteroidmining.exception;

/**
 * Exception thrown when a requested asteroid cannot be found in the system.
 */
public class AsteroidNotFoundException extends RuntimeException {
    public AsteroidNotFoundException(String message) {
        super(message);
    }
}
