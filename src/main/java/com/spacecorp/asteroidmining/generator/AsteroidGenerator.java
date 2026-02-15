package com.spacecorp.asteroidmining.generator;

import com.spacecorp.asteroidmining.domain.Asteroid;

public interface AsteroidGenerator {
    /**
     * Generates a new {@link Asteroid} with randomized attributes.
     * @return a fully populated, immutable Asteroid object.
     */
    Asteroid generate();
}
