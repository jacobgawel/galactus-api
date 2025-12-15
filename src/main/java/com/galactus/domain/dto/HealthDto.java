package com.galactus.domain.dto;

import com.galactus.domain.constants.HealthType;

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
