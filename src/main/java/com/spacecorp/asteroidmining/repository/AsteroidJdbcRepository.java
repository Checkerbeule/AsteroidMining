package com.spacecorp.asteroidmining.repository;

import com.spacecorp.asteroidmining.domain.Asteroid;
import com.spacecorp.asteroidmining.domain.ResourceType;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Profile("postgres")
public interface AsteroidJdbcRepository extends CrudRepository<Asteroid, Long>, AsteroidRepository {

    @Override
    @Query("SELECT * FROM asteroids WHERE distance <= :maxDistance")
    List<Asteroid> findInRange(double maxDistance);

    @Override
    @Query("""
        SELECT a.* FROM asteroids a
        JOIN asteroid_resource r ON a.id = r.asteroid_id
        WHERE r.resource_type = :resource
    """)
    List<Asteroid> findWithResource(ResourceType resource);
}
