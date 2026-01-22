package com.spacecorp.asteroidmining.repository;

import com.spacecorp.asteroidmining.domain.Asteroid;
import com.spacecorp.asteroidmining.domain.ResourceType;

import java.util.List;
import java.util.Optional;

public interface AsteroidRepository {
    List<Asteroid> findAll();

    Optional<Asteroid> findById(String id);

    Optional<Asteroid> findByName(String name);

    List<Asteroid> findInRange(double maxDistance);

    List<Asteroid> findWithResource(ResourceType resource);
}
