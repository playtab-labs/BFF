package com.playtab.bff.admin.stage.dto.response;

import java.util.Map;

public record AdminStageDto(
        Long id,
        Map<String, String> name,
        Map<String, String> locationDesc,
        int displayOrder,
        String createdAt,
        String updatedAt
) {}
