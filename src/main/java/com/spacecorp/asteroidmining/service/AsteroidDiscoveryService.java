package com.spacecorp.asteroidmining.service;

import com.spacecorp.asteroidmining.domain.Asteroid;
import com.spacecorp.asteroidmining.generator.AsteroidGenerator;
import com.spacecorp.asteroidmining.repository.AsteroidRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Service responsible for the process of discovering new asteroids in outer space.
 * <p>This service acts as the orchestrator between the simulation logic (the "chance"
 * of finding something in space) and the actual data generation.</p>
 */
@Service
public class AsteroidDiscoveryService {

    private final AsteroidGenerator asteroidGenerator;
    private final AsteroidRepository asteroidRepository;
    private final double discoveryRate;

    /**
     * Constructs the service with a specific generation strategy.
     *
     * @param asteroidGenerator the strategy used to create asteroid data,
     *                          injected based on the active configuration (e.g. {@code local, hybrid or llm}).
     */
    public AsteroidDiscoveryService(
            AsteroidGenerator asteroidGenerator,
            AsteroidRepository asteroidRepository,
            @Value("${asteroid.discovery.discovery-rate:0.7}") double discoveryRate) {
        this.asteroidGenerator = asteroidGenerator;
        this.asteroidRepository = asteroidRepository;
        this.discoveryRate = discoveryRate;
    }

    /**
     * Performs a simulated deep-space scan to discover a new asteroid.
     * <p>The method uses a probability-based approach to simulate the vastness of space,
     * where a discovery is not guaranteed. If the scan is successful (70% chance),
     * it delegates the creation of the asteroid's details to the injected generator.</p>
     *
     * @return an {@link Optional} containing the discovered {@link Asteroid},
     * or an empty Optional if the scan yielded no results.
     */
    public Optional<Asteroid> discoverNewAsteroid() {
        // We randomly generate a new asteroid to simulate if a new asteroid could be discovered in space.
        if (ThreadLocalRandom.current().nextDouble(1.) > discoveryRate) {
            return Optional.empty();
        }

        Asteroid newAsteroid = asteroidGenerator.generate();
        Asteroid newAsteroidWithId = asteroidRepository.save(newAsteroid);

        return Optional.of(newAsteroidWithId);
    }
}
