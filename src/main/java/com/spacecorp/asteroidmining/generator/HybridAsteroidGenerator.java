package com.spacecorp.asteroidmining.generator;

import com.spacecorp.asteroidmining.domain.Asteroid;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

/**
 * An AI-powered implementation of {@link AsteroidGenerator} that utilizes a Large Language Model (LLM)
 * to generate creative, thematic asteroid names.
 * <p>
 * This generator uses a "Hybrid" approach:
 * <ul>
 * <li><b>Creativity:</b> Names are generated via Mistral AI based on randomized themes.</li>
 * <li><b>Safety & fallback:</b> A simple validation logic ensures that even if the AI provides
 * malformed output, the system falls back to a safe, procedurally generated name.</li>
 * <li><b>Composition:</b> Further property generation is delegated to a {@link RandomAsteroidFactory}.</li>
 * </ul>
 * * Enabled only when {@code asteroid.generator.mode=hybrid} is set in the configuration.
 */
@Component
@ConditionalOnProperty(name = "asteroid.generator.mode", havingValue = "hybrid")
public class HybridAsteroidGenerator implements AsteroidGenerator {

    private final ChatClient chatClient;
    private final RandomAsteroidFactory asteroidFactory;
    /**
     * Pattern to ensure names contain only alphanumeric characters, dashes, and spaces.
     */
    private static final Pattern VALID_CHARACTERS = Pattern.compile("^[a-zA-Z0-9\\- ]+$");
    /**
     * List of creative descriptors act as a thematic seed for the generative process.
     */
    private static final List<String> THEMES = List.of(
            "ancient", "cybernetic", "gaseous", "crystalline", "volcanic",
            "frozen", "radioactive", "gazy", "rocky", "dusty", "dark",
            "shiny", "volatile", "cloudy", "botanic", "wild", "exotic"
    );

    public HybridAsteroidGenerator(ChatClient.Builder builder, RandomAsteroidFactory asteroidFactory) {
        // Configuring the AI with a system prompt to define its persona and a frame for the expected output.
        this.chatClient = builder
                .defaultSystem("""
                        You are a planetary naming expert.
                        Rules:
                        - Provide ONLY the name, no sentences, no punctuation.
                        - Example: 'Ceres-Alpha', 'Aris-Centurion', 'X-99-Eris', or 'Alpha-Lumina-V2'.
                        - Avoid common suffixes like '-Alpha', '-Prime', '-Major' or '-One'.
                        - Avoid common prefixes like 'Astraeus-' or 'Umbra-'.
                        - Avoid repetition.
                        """)
                .build();
        this.asteroidFactory = asteroidFactory;
    }

    /**
     * Generates a new Asteroid by prompting the AI for a name and enriching it with
     * random procedural generated attributes.
     * @return a fully populated {@link Asteroid} instance.
     */
    @Override
    public Asteroid generate() {
        // Select a random theme to ensure variety in the AI's output
        String randomTheme = THEMES.get(ThreadLocalRandom.current().nextInt(THEMES.size()));
        String asteroidName = (chatClient.prompt()
                .user(u -> u.text("Generate one creative asteroid name that sounds scientific, mystic and/or sci-fi " +
                                "and has a {theme} vibe.")
                        .param("theme", randomTheme)
                ))
                .call()
                .content();

        // Basic response validation.
        // We check for null/blank, excessive length (>25), word count (>3),
        // and illegal special characters to prevent react on inappropriate asteroid names.
        if (Objects.isNull(asteroidName) || asteroidName.isBlank()
                || asteroidName.length() > 25 || asteroidName.split("[\\s\\-]+").length > 3
                || !VALID_CHARACTERS.matcher(asteroidName).matches()) {
            // Fallback: Generate a safe name if the AI output is inappropriate.
            asteroidName = randomTheme + " asteroid " + UUID.randomUUID().toString().substring(0, 6);
        }

        return asteroidFactory.createWithName(asteroidName);
    }
}
