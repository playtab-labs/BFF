package com.playtab.bff.admin.performer.dto.response;

import java.util.Map;

public record AdminPerformerDto(
        Long id,
        Map<String, String> name,
        Map<String, String> description,
        String imageUrl,
        boolean isActive,
        String createdAt,
        String updatedAt
) {}
