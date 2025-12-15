package com.galactus.controllers.rest;

import com.galactus.domain.constants.HealthType;
import com.galactus.domain.dto.HealthDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/health")
public class HealthController {

    @GetMapping
    public HealthDto checkHealth() {
        return new HealthDto().setHealth(HealthType.HEALTHY).check();
    }
}
