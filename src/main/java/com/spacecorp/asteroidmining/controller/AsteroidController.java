package com.spacecorp.asteroidmining.controller;

import com.spacecorp.asteroidmining.domain.Asteroid;
import com.spacecorp.asteroidmining.domain.ResourceType;
import com.spacecorp.asteroidmining.service.AsteroidService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller acting as the primary entry point for asteroid-related API requests.
 * <p>
 * This class handles the transformation of incoming HTTP requests into service calls
 * and ensures that the resulting domain data is correctly serialized into JSON format.
 * </p>
 *
 * <b>Applied Clean Code Principles:</b>
 * <ul>
 * <li><b>Single Responsibility Principle (SRP):</b> The controller is only responsible
 * for request mapping, input validation, and HTTP response status management.</li>
 * <li><b>Dependency Injection (DI):</b> Dependencies are injected via the constructor.
 * This decouples the controller from the instantiation logic of the service,
 * making it easily testable with mocks.</li>
 * <li><b>KISS (Keep It Simple, Stupid/Staightforward):</b> The controller depends directly on the
 * concrete {@link AsteroidService} class. By avoiding unnecessary interfaces for
 * single-implementation services, we reduce boilerplate code and maintainability overhead.</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/v1/asteroids")
public class AsteroidController {

    private final AsteroidService asteroidService;

    // Constructor Dependency Injection
    // AsteroidService handles business logic.
    public AsteroidController(AsteroidService asteroidService) {
        this.asteroidService = asteroidService;
    }

    @GetMapping
    public List<Asteroid> getAllAsteroids() {
        return asteroidService.getAllAsteroids();
    }

    @GetMapping("/{id}")
    public Asteroid getAsteroidById(@PathVariable String id) {
        return asteroidService.getAsteroidById(id);
    }

    @GetMapping("/search/name")
    public Asteroid getAsteroidByName(@RequestParam String name) {
        return asteroidService.getAsteroidByName(name);
    }

    @GetMapping("/filter/range")
    public List<Asteroid> getAsteroidsInRange(@RequestParam double range) {
        return asteroidService.getAsteroidsInRange(range);
    }

    @GetMapping("/filter/resource")
    public List<Asteroid> getAsteroidsWithResource(@RequestParam ResourceType resource) {
        return asteroidService.getAsteroidsWithResource(resource);
    }

    @GetMapping("/filter/profitable")
    public List<Asteroid> getProfitableAsteroids(@RequestParam int minValue) {
        return asteroidService.getProfitableAsteroids(minValue);
    }
}
