package com.spacecorp.asteroidmining.service;

import com.spacecorp.asteroidmining.domain.Asteroid;
import com.spacecorp.asteroidmining.domain.ResourceType;
import com.spacecorp.asteroidmining.exception.AsteroidNotFoundException;
import com.spacecorp.asteroidmining.repository.AsteroidRepository;
import com.spacecorp.asteroidmining.repository.MiningMarketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsteroidService {

    private final AsteroidRepository asteroidRepo;
    private final MiningMarketRepository marketRepo;

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
