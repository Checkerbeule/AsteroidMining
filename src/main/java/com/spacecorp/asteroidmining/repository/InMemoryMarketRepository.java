package com.spacecorp.asteroidmining.repository;

import com.spacecorp.asteroidmining.domain.ResourceType;
import com.spacecorp.asteroidmining.service.AsteroidService;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * In-memory implementation of the {@link MiningMarketRepository}.
 * <p>
 * This class stores market data in a simple {@link Map}. It is designed
 * for rapid development, prototyping, and automated testing without the overhead
 * of a persistent database.
 * </p>
 * <b>Applied Clean Code Strategy:</b>
 * <ul>
 * <li><b>Separation of Concerns:</b> This class handles only the technical details
 * of data storage, while the service layer remains unaware of how the data is stored.</li>
 * <li><b>Liskov Substitution Principle (LSP):</b> This implementation can be
 * swapped with a Database-backed or web-API repository at any time without breaking the
 * {@link AsteroidService}.</li>
 * </ul>
 */
@Repository
public class InMemoryMarketRepository implements MiningMarketRepository {

    private final Map<ResourceType, Integer> resourcePrices;

    /**
     * Initializes the repository with hardcoded sample data (Bootstrapping).
     */
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
