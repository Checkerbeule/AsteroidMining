package com.spacecorp.asteroidmining.generator;

import com.spacecorp.asteroidmining.domain.Asteroid;
import com.spacecorp.asteroidmining.domain.ResourceType;
import com.spacecorp.asteroidmining.domain.RiskProfile;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Component
@ConditionalOnProperty(name = "asteroid.generator.mode", havingValue = "hybrid")
public class HybridAsteroidGenerator implements AsteroidGenerator {

    private final ChatClient chatClient;

    private static final List<String> THEMES = List.of(
            "ancient", "cybernetic", "gaseous", "crystalline", "volcanic",
            "frozen", "radioactive", "gazy", "rocky", "dusty", "dark",
            "shiny", "volatile", "cloudy", "botanic", "wild", "exotic"
    );

    public HybridAsteroidGenerator(ChatClient.Builder builder) {
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
    }

    @Override
    public Asteroid generate() {
        var random = ThreadLocalRandom.current();

        String randomTheme = THEMES.get(ThreadLocalRandom.current().nextInt(THEMES.size()));
        String asteroidName = (chatClient.prompt()
                .user(u -> u.text("Generate one creative asteroid name that sounds scientific, mystic and/or sci-fi " +
                                "and has a {theme} vibe.")
                        .param("theme", randomTheme)
                ))
                .call()
                .content()
                .replaceAll("[^a-zA-Z0-9\\- ]", "");


        double distance = random.nextDouble(100.);
        RiskProfile[] riskProfiles = RiskProfile.values();
        RiskProfile risk = riskProfiles[random.nextInt(riskProfiles.length)];
        var resources = generateRandomResources();

        return Asteroid.builder()
                .name(asteroidName)
                .riskProfile(risk)
                .distanceInLightYears(distance)
                .resources(resources)
                .build();
    }

    /**
     * Internal helper to simulate a distribution of resources.
     *
     * @return a map of {@link ResourceType} and their respective {@link Asteroid.ResourceAmount}.
     */
    private Map<ResourceType, Asteroid.ResourceAmount> generateRandomResources() {
        var random = ThreadLocalRandom.current();

        ResourceType[] resourceTypes = ResourceType.values();
        Map<ResourceType, Asteroid.ResourceAmount> resources = new HashMap<>();
        for (var resource : resourceTypes) {
            if (random.nextBoolean()) break;
            resources.put(resource, new Asteroid.ResourceAmount(random.nextInt(100_000)));
        }
        return resources;
    }
}
