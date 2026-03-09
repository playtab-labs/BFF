package com.playtab.bff.rest;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/api/v1/health")
    public Map<String, Object> Health() {
        return Map.of(
                "success", true,
                "message", "OK"
        );
    }
}
