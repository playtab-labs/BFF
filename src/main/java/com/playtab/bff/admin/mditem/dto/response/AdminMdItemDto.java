package com.playtab.bff.admin.mditem.dto.response;

import java.util.Map;

public record AdminMdItemDto(
        Long id,
        Map<String, String> name,
        int price,
        Map<String, String> productDescription,
        Map<String, String> detailDescription,
        String thumbnailImageUrl,
        String detailImageUrl,
        boolean isSoldOut,
        int displayOrder,
        boolean isVisible,
        String createdAt,
        String updatedAt
) {}
