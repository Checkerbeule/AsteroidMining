package com.spacecorp.asteroidmining.exception;

public class AsteroidNotFoundException extends RuntimeException {
    public AsteroidNotFoundException(String message) {
        super(message);
    }
}
