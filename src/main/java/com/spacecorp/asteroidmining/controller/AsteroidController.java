package com.spacecorp.asteroidmining.controller;

import com.spacecorp.asteroidmining.domain.Asteroid;
import com.spacecorp.asteroidmining.domain.ResourceType;
import com.spacecorp.asteroidmining.service.AsteroidService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/asteroids")
public class AsteroidController {

    private final AsteroidService asteroidService;

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
