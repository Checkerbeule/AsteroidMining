package com.spacecorp.asteroidmining.domain;

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
public record Asteroid (
        String id,
        String name,
        RiskProfile riskProfile,
        Map<ResourceType, Integer> resources,
        double distanceInLightYears
        ) {
}