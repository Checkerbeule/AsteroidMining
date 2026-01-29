package com.spacecorp.asteroidmining;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "AsteroidMining API",
				version = "1.0",
				description = "API documentation for mining activities on asteroids"
		)
)
@SpringBootApplication
public class AsteroidMiningApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsteroidMiningApiApplication.class, args);
	}

}
