package com.playtab.bff.admin.pub.dto.request;

import java.util.Map;

public record AdminPubRequestDto(
        Map<String, String> collegeName,
        boolean isNameConfirmed,
        String thumbnailImageUrl,
        int displayOrder,
        boolean isVisible
) {}
