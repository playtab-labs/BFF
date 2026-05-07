package com.playtab.bff.admin.mdoptionvalue.dto.request;

import java.util.Map;

public record AdminMdOptionValueRequestDto(
        Long optionGroupId,
        Map<String, String> valueName,
        int extraPrice,
        boolean isSoldOut,
        int displayOrder
) {}
