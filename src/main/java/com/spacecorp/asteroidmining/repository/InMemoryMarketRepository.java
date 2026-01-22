package com.spacecorp.asteroidmining.repository;

import com.spacecorp.asteroidmining.domain.ResourceType;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class InMemoryMarketRepository implements MiningMarketRepository {

    private final Map<ResourceType, Integer> resourcePrices;

    public InMemoryMarketRepository() {
        resourcePrices = Map.of(
                ResourceType.IRON, 15,
                ResourceType.GOLD, 120,
                ResourceType.PLATINUM, 300,
                ResourceType.KRYPTONITE, 1500
        );
    }

    @Override
    public int getPriceFor(ResourceType resource) {
        return resourcePrices.getOrDefault(resource, 0);
    }

    @Override
    public Map<ResourceType, Integer> getMarketPrices() {
        return resourcePrices;
    }
}
