package com.galactus.common.flags;

import com.netflix.archaius.DefaultPropertyFactory;
import com.netflix.archaius.api.Property;
import org.springframework.stereotype.Service;

@Service
public class FeatureFlags {
    private final Property<String> appName;
    private final Property<Boolean> featureToggle;

    public FeatureFlags(DefaultPropertyFactory propertyFactory) {
        this.appName = propertyFactory.get("app.name", String.class);
        this.featureToggle = propertyFactory.get("app.feature.toggle", Boolean.class);
    }

    public String getAppName() {
        return appName.get();
    }

    public boolean isFeatureToggle() {
        return featureToggle.get();
    }
}
