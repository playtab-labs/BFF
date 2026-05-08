package com.playtab.bff.admin.performer.dto.request;

import java.util.Map;

public record AdminPerformerRequestDto(
        Map<String, String> name,
        Map<String, String> description,
        String imageUrl,
        boolean isActive
) {}
