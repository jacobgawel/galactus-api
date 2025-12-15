package com.galactus.health.dto;

import com.galactus.common.constants.HealthType;

public class HealthDto {
    public HealthType Status;

    public HealthDto setHealth(HealthType type) {
        this.Status = type;
        return this;
    }

    public HealthDto check() {
        return this;
    }
}
