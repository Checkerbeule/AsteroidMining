package com.spacecorp.asteroidmining.exception;

/**
 * Exception thrown when an asteroid discovery fails (e.g. due unavailability, broken telescope, solar storm interferences).
 */
public class AsteroidDiscoveryException extends RuntimeException {
    public AsteroidDiscoveryException(String message, Throwable cause) {
        super(message, cause);
    }
}
