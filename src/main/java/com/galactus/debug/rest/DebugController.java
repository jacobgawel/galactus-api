package com.galactus.debug.rest;

import com.galactus.common.flags.FeatureFlags;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/debug")
public class DebugController {
    private final FeatureFlags featureFlags;

    public DebugController(FeatureFlags featureFlags) {
        this.featureFlags = featureFlags;
    }

    @GetMapping("name")
    public String name() {
        return featureFlags.getAppName();
    }

    @GetMapping("feature")
    public Boolean feature() {
        return featureFlags.isFeatureToggle();
    }
}
