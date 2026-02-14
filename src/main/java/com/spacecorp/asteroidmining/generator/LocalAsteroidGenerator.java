package com.spacecorp.asteroidmining.generator;

import com.spacecorp.asteroidmining.domain.Asteroid;
import com.spacecorp.asteroidmining.domain.ResourceType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Implementation of the {@link AsteroidGenerator} that operates entirely locally.
 * * <p>This generator uses local random algorithms to simulate asteroid discovery without
 * relying on external APIs or Cloud LLMs. It is designed for offline development,
 * testing, or as a cost-free fallback strategy.</p>
 * * <p>The generation logic includes:</p>
 * <ul>
 * <li><b>Naming:</b> Uses a combination of a prefix and a shortened UUID.</li>
 * <li><b>Environment:</b> Randomly assigns risk profiles and distances using {@link ThreadLocalRandom}.</li>
 * <li><b>Resources:</b> Dynamically generates a resource map based on available {@link ResourceType}s.</li>
 * </ul>
 * * <p>This component is only loaded if the property {@code asteroid.generator.mode}
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
