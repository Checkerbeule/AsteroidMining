package com.spacecorp.asteroidmining.domain;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import com.spacecorp.asteroidmining.repository.InMemoryAsteroidRepository;

import java.util.Map;

/**
 * Domain model representing an asteroid as an immutable data carrier.
 * <p>This entity uses a Java Record to ensure data integrity and reduce boilerplate code.</p>
 *
 * <b>Applied Clean Code Principles:</b>
 * <ul>
 * <li><b>Immutability:</b> Once created, the state of an asteroid cannot be
 * altered, preventing accidental side effects across different layers.</li>
 *
 * @param id                   Unique identifier for the asteroid (typically a UUID).
 * @param name                 Human-readable designation (e.g., "Ceres-Alpha").
 * @param riskProfile          The safety classification for mining operations.
 * @param resources            A map containing available resource types and their respective quantities.
 * @param distanceInLightYears The spatial distance from the central station, used for fuel and time calculations.
 */
@Table("asteroids")
@Builder
public record Asteroid(
        @Id
        Long id,
        String name,
        RiskProfile riskProfile,
        @MappedCollection(idColumn = "asteroid_id", keyColumn = "resource_type")
        Map<ResourceType, ResourceAmount> resources,
        @Column("distance")
        double distanceInLightYears
) {
    /**
     * Creates a copy of this asteroid with a new ID.
     * Used primarily by the {@link InMemoryAsteroidRepository} during the persist process.
     */
    public Asteroid withId(Long id) {
        return Asteroid.builder()
                .id(id)
                .name(this.name)
                .riskProfile(this.riskProfile)
                .distanceInLightYears(this.distanceInLightYears)
                .resources(this.resources)
                .riskProfile(this.riskProfile)
                .build();
    }

    /**
     * Wrapper for the resource amount to force Spring Data JDBC to use
     * the 'asteroid_resource' join table.
     * * Note: Without this wrapper, Spring incorrectly looks for a 'resources'
     * column in the main 'asteroids' table.
     *
     * @param amount The quantity of the specific resource.
     */
    @Table("asteroid_resource")
    public record ResourceAmount(
            Integer amount
    ) {
    }
}
