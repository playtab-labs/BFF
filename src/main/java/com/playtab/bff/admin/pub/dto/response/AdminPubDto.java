package com.playtab.bff.admin.pub.dto.response;

import java.util.Map;

public record AdminPubDto(
        Long id,
        Map<String, String> collegeName,
        boolean isNameConfirmed,
        String thumbnailImageUrl,
        int displayOrder,
        boolean isVisible,
        String createdAt,
        String updatedAt
) {}
