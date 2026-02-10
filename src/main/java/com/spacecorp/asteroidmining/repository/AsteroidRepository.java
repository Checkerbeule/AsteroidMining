package com.spacecorp.asteroidmining.repository;

import com.spacecorp.asteroidmining.domain.Asteroid;
import com.spacecorp.asteroidmining.domain.ResourceType;

import java.util.List;
import java.util.Optional;

/**
 * Interface for managing the abstraction of asteroid data storage.
 * <p>
 * This interface provides a contract for data access, shielding the business
 * logic from the specific storage implementation (e.g., In-Memory, SQL, or NoSQL).
 * </p>
 *
 * <b>Applied Clean Code Principles:</b>
 * <ul>
 * <li><b>Single Responsibility Principle (SRP):</b> This interface has only one
 * reason to change: a change in the data access requirements for asteroids.</li>
 * <li><b>Dependency Inversion Principle (DIP):</b> High-level services depend
 * on this abstraction, not on a concrete storage implementation.</li>
 * <li><b>Interface Segregation Principle (ISP):</b> By keeping the interface
 * focused specifically on Asteroid entities, clients are not forced to
 * depend on methods for other domain objects.</li>
 * </ul>
 */
public interface AsteroidRepository {
    /**
     * @return a list of all asteroids currently in the system.
     */
    List<Asteroid> findAll();

    /**
     * @param id unique identifier.
     * @return an Optional containing the result.
     */
    Optional<Asteroid> findById(Long id);

    /**
     * @param name exact name of the asteroid.
     * @return an Optional containing the result.
     */
    Optional<Asteroid> findByName(String name);

    /**
     * @param maxDistance maximum light years.
     * @return asteroids within this range.
     */
    List<Asteroid> findInRange(double maxDistance);

    /**
     * @param resource the required material.
     * @return asteroids containing this resource.
     */
    List<Asteroid> findWithResource(ResourceType resource);
}
