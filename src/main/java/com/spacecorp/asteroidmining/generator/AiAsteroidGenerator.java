package com.spacecorp.asteroidmining.generator;

import com.spacecorp.asteroidmining.domain.Asteroid;
import com.spacecorp.asteroidmining.domain.ResourceType;
import com.spacecorp.asteroidmining.exception.AiGenerationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.retry.NonTransientAiException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * AI-driven implementation of the {@link AsteroidGenerator}.
 * <p>
 * This generator uses a Large Language Model (LLM) via Spring AI to create unique asteroid data.
 * It is only active if {@code asteroid.generator.mode} is set to 'llm'.
 */
@Component
@ConditionalOnProperty(name = "asteroid.generator.mode", havingValue = "llm")
public class AiAsteroidGenerator implements AsteroidGenerator {
    private static final Logger log = LoggerFactory.getLogger(AiAsteroidGenerator.class);

    private final ChatClient chatClient;
    private final BeanOutputConverter<Asteroid> beanOutputConverter;

    public AiAsteroidGenerator(ChatClient.Builder chatBuilder) {
        // Configuring the AI with a system prompt to define its persona and a frame for the expected output.
        this.chatClient = chatBuilder
                .defaultSystem("""
                        You are a planetary naming expert. Create realistic but creative asteroid data.
                        Rules:
                        - The name should look similar to these: 'Ceres-Alpha', 'Aris-Centurion', 'X-99-Eris', or 'Alpha-Lumina-V2'.
                        - Avoid common suffixes like '-Alpha', '-Prime', '-Major' or '-One'.
                        - Avoid common prefixes like 'Astraeus-' or 'Umbra-'.
                        - Avoid repetition.
                        - Ensure distance is between 0 and 100.
                        - Ensure resource amounts are between 0 and 100000.
                        """)
                .build();
        this.beanOutputConverter = new BeanOutputConverter<>(Asteroid.class);
        log.debug("JSON schema for ai: {}", beanOutputConverter.getJsonSchema());
    }

    /**
     * Generates a new unique {@link Asteroid} by prompting the AI.
     * * @return A valid Asteroid record parsed from the AI response.
     * @throws AiGenerationException if the AI service is unavailable or the output is malformed.
     */
    @Override
    public Asteroid generate() {
        String rawJson = "";
        try {
            String validResources = Stream.of(ResourceType.values())
                    .map(Enum::name)
                    .collect(Collectors.joining(", "));

            rawJson = chatClient.prompt()
                    .user(u -> u
                            .text("""
                                    Generate one unique asteroid.
                                    Use a random sci-fi theme.
                                    Ensure the resource amounts are realistic.
                                    Valid resource types are: {resourceTypes}
                                    {format}
                                    """)
                            .param("resourceTypes", validResources)
                            .param("format", beanOutputConverter.getFormat())
                    )
                    .call()
                    .content();
            return beanOutputConverter.convert(rawJson);

        } catch (NonTransientAiException e) {
            throw new AiGenerationException("AI Service communication failed", e);
        } catch (Exception e) {
            log.error("AI delivered invalid JSON: {}", rawJson);
            throw new AiGenerationException("AI output was invalid", e);
        }
    }
}
