package com.playtab.bff.admin.foodtruck.dto.response;

import java.util.Map;

public record AdminFoodTruckDto(
        Long id,
        Map<String, String> name,
        Map<String, String> shortDescription,
        String thumbnailImageUrl,
        int displayOrder,
        boolean isVisible,
        String createdAt,
        String updatedAt
) {}
