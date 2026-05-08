package com.playtab.bff.admin.stage.dto.request;

import java.util.Map;

public record AdminStageRequestDto(
        Map<String, String> name,
        Map<String, String> locationDesc,
        int displayOrder
) {}
