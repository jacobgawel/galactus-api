package com.galactus.api.config.configuration;

import com.galactus.api.config.props.ArchaiusSettings;
import com.netflix.archaius.DefaultPropertyFactory;
import com.netflix.archaius.api.config.CompositeConfig;
import com.netflix.archaius.api.exceptions.ConfigException;
import com.netflix.archaius.config.DefaultCompositeConfig;
import com.netflix.archaius.config.MapConfig;
import com.netflix.archaius.config.PollingDynamicConfig;
import com.netflix.archaius.config.polling.FixedPollingStrategy;
import com.netflix.archaius.readers.URLConfigReader;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableConfigurationProperties(ArchaiusSettings.class)
public class ArchaiusConfiguration {
    /**
     * Creates the Archaius CompositeConfig and adds:
     * - Spring Environment snapshot (so Archaius can "see" your Boot properties)
     * - Optional polled dynamic .properties snapshot (file: or http:)
     * - Optional hardcoded defaults
     */
    @Bean
    public CompositeConfig archaiusConfig(ArchaiusSettings settings) throws ConfigException {
        CompositeConfig config = DefaultCompositeConfig.create();

        // 1) Remote dynamic snapshot (polled)
        if (settings.dynamicUrl() != null) {
            int seconds = (int) settings.pollingInterval().toSeconds();

            PollingDynamicConfig remote = new PollingDynamicConfig(
                    new URLConfigReader(settings.dynamicUrl()),
                    new FixedPollingStrategy(seconds, TimeUnit.SECONDS)
            );

            config.addConfig("REMOTE", remote);
        }

        // 2) Local defaults (lowest priority)
        Map<String, String> defaults = settings.defaults() == null
                ? Map.of()
                : Map.copyOf(settings.defaults());

        config.addConfig("DEFAULTS", new MapConfig(defaults));

        return config;
    }

    @Bean
    public DefaultPropertyFactory propertyFactory(CompositeConfig config) {
        return DefaultPropertyFactory.from(config);
    }
}
