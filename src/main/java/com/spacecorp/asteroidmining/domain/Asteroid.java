package com.spacecorp.asteroidmining.domain;

import java.util.Map;

public record Asteroid (
        String id,
        String name,
        RiskProfile riskProfile,
        Map<ResourceType, Integer> resources,
        double distanceInLightYears
        ) {
}