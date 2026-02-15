package com.spacecorp.asteroidmining.generator;

import com.spacecorp.asteroidmining.domain.Asteroid;
import com.spacecorp.asteroidmining.domain.ResourceType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Implementation of the {@link AsteroidGenerator} that operates entirely locally.
 * <p>This generator uses local random algorithms to create new asteroids for discovery simulation
 * without relying on external APIs or Cloud LLMs. It is designed for offline development,
 * testing, or as a cost-free fallback strategy.</p>
 * <p>This component is only loaded if the property {@code asteroid.generator.mode}
 * is set to {@code local}.</p>
 */
@Component
@ConditionalOnProperty(name = "asteroid.generator.mode", havingValue = "local")
public class LocalAsteroidGenerator implements AsteroidGenerator {

    private final RandomAsteroidFactory asteroidFactory;

    public LocalAsteroidGenerator(RandomAsteroidFactory asteroidFactory) {
        this.asteroidFactory = asteroidFactory;
    }

    @Override
    public Asteroid generate() {
        String name = "Asteroid " + UUID.randomUUID().toString().substring(0, 6);

        return asteroidFactory.createWithName(name);
    }
}
