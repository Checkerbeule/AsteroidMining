package com.spacecorp.asteroidmining.repository;

import com.spacecorp.asteroidmining.domain.Asteroid;
import com.spacecorp.asteroidmining.domain.ResourceType;
import com.spacecorp.asteroidmining.domain.RiskProfile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryAsteroidRepository implements AsteroidRepository {

    private final List<Asteroid> asteroids = new ArrayList<>();

    public InMemoryAsteroidRepository() {
        asteroids.add(new Asteroid(
                "1", "Ceres-Alpha", RiskProfile.SAFE,
                Map.of(ResourceType.IRON, 500, ResourceType.GOLD, 10), 2.5
        ));
        asteroids.add(new Asteroid(
                "2", "X-99-Eris", RiskProfile.CAUTION,
                Map.of(ResourceType.KRYPTONITE, 5, ResourceType.PLATINUM, 5), 15.1
        ));
        asteroids.add(new Asteroid(
                "3", "Alpha-Lumina-V2", RiskProfile.VOLATILE,
                Map.of(ResourceType.IRON, 500, ResourceType.PLATINUM, 1, ResourceType.GOLD, 2), 5.7
        ));
        asteroids.add(new Asteroid(
                "4", "Aris-Centurion", RiskProfile.LETHAL,
                Map.of(ResourceType.KRYPTONITE, 1500), 25.3
        ));
    }

    @Override
    public List<Asteroid> findAll() {
        return asteroids;
    }

    @Override
    public Optional<Asteroid> findById(String id) {
        return asteroids.stream()
                .filter(asteroid -> Objects.equals(asteroid.id(), id))
                .findFirst();
    }

    @Override
    public Optional<Asteroid> findByName(String name) {
        return asteroids.stream()
                .filter(asteroid -> Objects.equals(asteroid.name(), name))
                .findFirst();
    }

    @Override
    public List<Asteroid> findInRange(double maxDistance) {
        return asteroids.stream()
                .filter(asteroid -> asteroid.distanceInLightYears() <= maxDistance)
                .toList();
    }

    @Override
    public List<Asteroid> findWithResource(ResourceType resource) {
        return asteroids.stream()
                .filter(asteroid -> asteroid.resources().containsKey(resource))
                .toList();
    }
}
