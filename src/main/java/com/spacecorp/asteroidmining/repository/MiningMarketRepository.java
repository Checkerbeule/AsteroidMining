package com.spacecorp.asteroidmining.repository;

import com.spacecorp.asteroidmining.domain.ResourceType;

import java.util.Map;

/**
 * Interface for managing the abstraction of mining market data storage.
 * It delivers current market prices for {@link ResourceType} found on asteroids.
 * <p>
 * This interface provides a contract for data access, shielding the business
 * logic from the specific storage implementation (e.g., In-Memory, SQL, or other web-APIs).
 * </p>
 *
 * <b>Applied Clean Code Principles:</b>
 * <ul>
 * <li><b>Single Responsibility Principle (SRP):</b> This interface has only one
 * reason to change: a change in the data access requirements for mining market data.</li>
 * <li><b>Dependency Inversion Principle (DIP):</b> High-level services depend
 * on this abstraction, not on a concrete storage implementation.</li>
 * <li><b>Interface Segregation Principle (ISP):</b> By keeping the interface
 * focused specifically on mining market data, clients are not forced to
 * depend on methods for other domain objects.</li>
 * </ul>
 */
public interface MiningMarketRepository {

    /**
     * Retrieves the current market price for a specific resource.
     * @param resource the {@link ResourceType} to look up.
     *
     * @return the current price in credits.
     * @throws IllegalArgumentException if the resource type is not supported by the market.
     */
    int getPriceFor(ResourceType resource);

    /**
     * Retrieves a complete snapshot of all current market prices.
     * @return an immutable map of all resources and their respective prices.
     */
    Map<ResourceType, Integer> getMarketPrices();
}
