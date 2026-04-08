package com.playtab.bff.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "System", description = "시스템 API")
@RestController
public class HealthController {

    @Operation(summary = "서버 상태 확인 (헬스체크)")
    @GetMapping("/api/v1/health")
    public Map<String, Object> Health() {
        return Map.of(
                "success", true,
                "message", "OK"
        );
    }
}
