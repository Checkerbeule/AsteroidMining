package com.spacecorp.asteroidmining.repository;

import com.spacecorp.asteroidmining.domain.ResourceType;

import java.util.Map;

public interface MiningMarketRepository {

    int getPriceFor(ResourceType resource);

    Map<ResourceType, Integer> getMarketPrices();
}
