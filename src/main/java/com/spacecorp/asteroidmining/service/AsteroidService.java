package com.spacecorp.asteroidmining.service;

import com.spacecorp.asteroidmining.domain.Asteroid;
import com.spacecorp.asteroidmining.domain.ResourceType;
import com.spacecorp.asteroidmining.exception.AsteroidNotFoundException;
import com.spacecorp.asteroidmining.repository.AsteroidRepository;
import com.spacecorp.asteroidmining.repository.MiningMarketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for managing asteroid operations and financial valuations.
 * <p>
 * This service acts as the central orchestrator, coordinating between
 * the {@link AsteroidRepository} and the {@link MiningMarketRepository}.
 * </p>
 *
 * <b>Applied Clean Code Strategy:</b>
 * <ul>
 * <li><b>Dependency Injection (DI):</b> Uses constructor injection to receive
 * repository abstractions, facilitating easier unit testing with mocks.</li>
 * <li><b>Facade Pattern:</b> Provides a simplified interface to the controller
 * while hiding the complexity of multi-repository data aggregation.</li>
 * <li><b>KISS (Keep It Simple, Stupid/Staightforward):</b> By avoiding unnecessary interfaces for
 * single-implementation services, we reduce boilerplate code and maintainability overhead.</li>
 * <li><b>Error Signaling:</b> Methods throw {@link AsteroidNotFoundException}
 * to signal missing domain objects, allowing the global handler to manage responses.</li>
 * </ul>
 */
@Service
public class AsteroidService {

    private final AsteroidRepository asteroidRepo;
    private final MiningMarketRepository marketRepo;

    // Constructor Dependency Injection
    public AsteroidService(AsteroidRepository asteroidRepo, MiningMarketRepository miningMarketRepo) {
        this.asteroidRepo = asteroidRepo;
        this.marketRepo = miningMarketRepo;
    }

    public List<Asteroid> getAllAsteroids() {
        return asteroidRepo.findAll();
    }

    public Asteroid getAsteroidById(String id) {
        return asteroidRepo.findById(id)
                .orElseThrow(() -> new AsteroidNotFoundException("Asteroid with ID " + id + " not found!"));
    }

    public Asteroid getAsteroidByName(String name) {
        return asteroidRepo.findByName(name)
                .orElseThrow(() -> new AsteroidNotFoundException("Asteroid with name " + name + " not found!"));
    }

    public List<Asteroid> getAsteroidsInRange(double range) {
        return asteroidRepo.findInRange(range);
    }

    public List<Asteroid> getAsteroidsWithResource(ResourceType resource) {
        return asteroidRepo.findWithResource(resource);
    }

    public List<Asteroid> getProfitableAsteroids(int minTotalValue) {
        return asteroidRepo.findAll().stream()
                .filter(asteroid -> calculateTotalValue(asteroid) >= minTotalValue)
                .toList();
    }

    private int calculateTotalValue(Asteroid asteroid) {
        return asteroid.resources().entrySet().stream()
                .mapToInt(entry -> {
                    int pricePerUnit = marketRepo.getPriceFor(entry.getKey());
                    return pricePerUnit * entry.getValue();
                })
                .sum();
    }
}
