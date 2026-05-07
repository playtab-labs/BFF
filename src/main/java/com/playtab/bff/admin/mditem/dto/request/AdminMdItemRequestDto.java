package com.playtab.bff.admin.mditem.dto.request;

import java.util.Map;

public record AdminMdItemRequestDto(
        Map<String, String> name,
        int price,
        Map<String, String> productDescription,
        Map<String, String> detailDescription,
        String thumbnailImageUrl,
        String detailImageUrl,
        boolean isSoldOut,
        int displayOrder,
        boolean isVisible
) {}
