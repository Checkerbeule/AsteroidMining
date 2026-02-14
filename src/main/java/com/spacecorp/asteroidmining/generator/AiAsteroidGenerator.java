package com.spacecorp.asteroidmining.generator;


import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.spacecorp.asteroidmining.domain.Asteroid;
import com.spacecorp.asteroidmining.domain.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
@ConditionalOnProperty(name = "asteroid.generator.mode", havingValue = "llm")
public class AiAsteroidGenerator implements AsteroidGenerator {
    private static final Logger log = LoggerFactory.getLogger(AiAsteroidGenerator.class);

    private final ChatClient chatClient;
    private final RandomAsteroidFactory fallbackFactory;

    public AiAsteroidGenerator(ChatClient.Builder chatBuilder, RandomAsteroidFactory fallbackFactory) {
        this.chatClient = chatBuilder
                .defaultSystem("""
                        You are a planetary naming expert. Create realistic but creative asteroid data.
                        Rules:
                        - The name should look similar to these: 'Ceres-Alpha', 'Aris-Centurion', 'X-99-Eris', or 'Alpha-Lumina-V2'.
                        - Avoid common suffixes like '-Alpha', '-Prime', '-Major' or '-One'.
                        - Avoid common prefixes like 'Astraeus-' or 'Umbra-'.
                        - Avoid repetition.
                        - Ensure distance is greater than 0.
                        - Ensure resource amounts are greater than 0 or do not exist.
                        """)
                .build();
        this.fallbackFactory = fallbackFactory;
    }

    @Override
    public Asteroid generate() {
        var converter = new BeanOutputConverter<>(Asteroid.class);
        log.debug(converter.getJsonSchema());

        try {
            String validResources = Stream.of(ResourceType.values())
                    .map(Enum::name)
                    .collect(Collectors.joining(", "));

            return chatClient.prompt()
                    .user(u -> u
                            .text("""
                                Generate one unique asteroid.
                                Use a random sci-fi theme.
                                Ensure the resource amounts are realistic.
                                Valid resource types are: {resourceTypes}
                                {format}
                                """)
                            .param("resourceTypes", validResources)
                            .param("format", converter.getFormat())
                    )
                    .call()
                    .entity(Asteroid.class);

        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            // Falls das LLM kein valides JSON liefert oder halluziniert: Fallback!
            return fallbackFactory.createWithName("Fallback-Asteroid-" + System.currentTimeMillis());
        }
    }
}
