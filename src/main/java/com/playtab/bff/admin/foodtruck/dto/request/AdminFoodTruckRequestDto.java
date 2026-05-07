package com.playtab.bff.admin.foodtruck.dto.request;

import java.util.Map;

public record AdminFoodTruckRequestDto(
        Map<String, String> name,
        Map<String, String> shortDescription,
        String thumbnailImageUrl,
        int displayOrder,
        boolean isVisible
) {}
