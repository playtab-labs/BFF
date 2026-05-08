package com.playtab.bff.admin.mdoptionvalue.dto.response;

import java.util.Map;

public record AdminMdOptionValueDto(
        Long id,
        Long optionGroupId,
        Map<String, String> valueName,
        int extraPrice,
        boolean isSoldOut,
        int displayOrder,
        String createdAt,
        String updatedAt
) {}
