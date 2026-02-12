package com.spacecorp.asteroidmining.controller;

import com.spacecorp.asteroidmining.domain.Asteroid;
import com.spacecorp.asteroidmining.domain.ResourceType;
import com.spacecorp.asteroidmining.service.AsteroidDiscoveryService;
import com.spacecorp.asteroidmining.service.AsteroidService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
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
@Tag(name = "Asteroid Discovery", description = "Endpoints for deep-space exploration and asteroid management")
public class AsteroidController {

    private final AsteroidService asteroidService;
    private final AsteroidDiscoveryService discoveryService;

    // Constructor Dependency Injection
    // Services handle business logic
    public AsteroidController(AsteroidService asteroidService, AsteroidDiscoveryService discoveryService) {
        this.asteroidService = asteroidService;
        this.discoveryService = discoveryService;
    }

    @GetMapping
    public List<Asteroid> getAllAsteroids() {
        return asteroidService.getAllAsteroids();
    }

    @GetMapping("/{id}")
    public Asteroid getAsteroidById(@PathVariable Long id) {
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

    /**
     * Triggers a deep-space scan to discover a new asteroid.
     * @return 200 if found, 204 if no new asteroid could be found.
     */
    @Operation(
            summary = "Discover a new asteroid",
            description = "Triggers a probability-based scan. Returns an asteroid or no content based on discovery chance."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Asteroid successfully discovered"),
            @ApiResponse(responseCode = "204", description = "Scan completed, but no asteroid was found")
    })
    @PostMapping("/discover")
    public ResponseEntity<Asteroid> discoverNewAsteroid() {
        return discoveryService.discoverNewAsteroid()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }
}
