package com.galactus.api.config.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

// this is a custom spring-bound record for archaius configuration within application.yml
@ConfigurationProperties(prefix = "archaius")
public record ArchaiusSettings(
    String dynamicUrl,
    Duration pollingInterval,
    Map<String, String> defaults
) {
    public ArchaiusSettings {
        if (pollingInterval == null) {
            pollingInterval = Duration.ofSeconds(5);
        }
        if (defaults == null) {
            defaults = new HashMap<>();
            defaults.put("dynamicUrl", "F:/Java/galactus-api/src/main/resources/config.properties");
        }
    }
}
