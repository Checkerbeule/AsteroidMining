package com.spacecorp.asteroidmining.generator;

import com.spacecorp.asteroidmining.domain.Asteroid;
import com.spacecorp.asteroidmining.domain.ResourceType;
import com.spacecorp.asteroidmining.domain.RiskProfile;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Factory responsible for the random creation of {@link Asteroid} objects.
 * This factory decouples asteroid data generation (distance, risk, resources) from the naming logic.
 * By centralizing this logic, we avoid code duplication across different generator
 * implementations and maintain a single point of truth for procedural asteroid generation.
 */
@Component
public class RandomAsteroidFactory {

    /**
     * Composes a complete {@link Asteroid} object by combining a provided name with
     * procedurally generated attributes.
     *
     * @param name The name for the asteroid.
     * @return A fully populated {@link Asteroid} instance with random distance,
     * risk profile, and resource distribution.
     */
    public Asteroid createWithName(String name) {
        var random = ThreadLocalRandom.current();

        double distance = random.nextDouble(100.);
        RiskProfile[] riskProfiles = RiskProfile.values();
        RiskProfile risk = riskProfiles[random.nextInt(riskProfiles.length)];
        var resources = generateRandomResources();

        return Asteroid.builder()
                .name(name)
                .riskProfile(risk)
                .distanceInLightYears(distance)
                .resources(resources)
                .build();
    }

    /**
     * Internal helper to simulate a distribution of resources.
     * @return a map of {@link ResourceType} and their respective {@link Asteroid.ResourceAmount}.
     */
    private Map<ResourceType, Asteroid.ResourceAmount> generateRandomResources() {
        var random = ThreadLocalRandom.current();

        ResourceType[] resourceTypes = ResourceType.values();
        Map<ResourceType, Asteroid.ResourceAmount> resources = new HashMap<>();
        for (var resource : resourceTypes) {
            if (random.nextBoolean()) continue;
            resources.put(resource, new Asteroid.ResourceAmount(random.nextInt(100_000)));
        }
        return resources;
    }
}
