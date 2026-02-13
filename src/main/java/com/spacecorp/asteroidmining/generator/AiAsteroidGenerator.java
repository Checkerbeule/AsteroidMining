package com.spacecorp.asteroidmining.generator;

import com.spacecorp.asteroidmining.domain.Asteroid;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name="asteroid.generator.mode", havingValue="llm")
public class AiAsteroidGenerator implements AsteroidGenerator {
    @Override
    public Asteroid generate() {
        return null;
    }
}
