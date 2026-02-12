package com.spacecorp.asteroidmining.repository;

import com.spacecorp.asteroidmining.domain.Asteroid;
import com.spacecorp.asteroidmining.domain.ResourceType;
import com.spacecorp.asteroidmining.domain.RiskProfile;
import com.spacecorp.asteroidmining.service.AsteroidService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * In-memory implementation of the {@link AsteroidRepository}.
 * <p>
 * This class stores asteroid data in a simple {@link ArrayList}. It is designed
 * for rapid development, prototyping, and automated testing without the overhead
 * of a persistent database.
 * </p>
 * <b>Applied Clean Code Strategy:</b>
 * <ul>
 * <li><b>Separation of Concerns:</b> This class handles only the technical details
 * of data storage, while the service layer remains unaware of how the data is stored.</li>
 * <li><b>Liskov Substitution Principle (LSP):</b> This implementation can be
 * swapped with a Database-backed repository at any time without breaking the
 * {@link AsteroidService}.</li>
 * </ul>
 * <p>
 * Note: We use {@link Profile} to activate this "mock" implementation only when the
 * 'postgres' profile is <b>not</b> active, providing a fallback for local testing
 * or environments without a running database.
 * </p>
 */
@Repository
@Profile("!postgres")
public class InMemoryAsteroidRepository implements AsteroidRepository {

    private final List<Asteroid> asteroids = new CopyOnWriteArrayList<>();
    private final AtomicLong idSequence;

    /**
     * Initializes the repository with hardcoded sample data (Bootstrapping).
     */
    public InMemoryAsteroidRepository() {
        asteroids.add(new Asteroid(
                1L, "Ceres-Alpha", RiskProfile.SAFE,
                Map.of(ResourceType.IRON, new Asteroid.ResourceAmount(500), ResourceType.GOLD, new Asteroid.ResourceAmount(10)), 2.5
        ));
        asteroids.add(new Asteroid(
                2L, "X-99-Eris", RiskProfile.CAUTION,
                Map.of(ResourceType.KRYPTONITE, new Asteroid.ResourceAmount(5), ResourceType.PLATINUM, new Asteroid.ResourceAmount(5)), 15.1
        ));
        asteroids.add(new Asteroid(
                3L, "Alpha-Lumina-V2", RiskProfile.VOLATILE,
                Map.of(ResourceType.IRON, new Asteroid.ResourceAmount(500), ResourceType.PLATINUM, new Asteroid.ResourceAmount(1), ResourceType.GOLD, new Asteroid.ResourceAmount(2)), 5.7
        ));
        asteroids.add(new Asteroid(
                4L, "Aris-Centurion", RiskProfile.LETHAL,
                Map.of(ResourceType.KRYPTONITE, new Asteroid.ResourceAmount(1500)), 25.3
        ));

        idSequence = new AtomicLong(asteroids.size());
    }

    @Override
    public List<Asteroid> findAll() {
        return Collections.unmodifiableList(asteroids);
    }

    @Override
    public Optional<Asteroid> findById(Long id) {
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

    @Override
    public Asteroid save(Asteroid asteroid) {
        var withId = asteroid.withId(idSequence.incrementAndGet());
        asteroids.add(withId);
        return withId;
    }
}
