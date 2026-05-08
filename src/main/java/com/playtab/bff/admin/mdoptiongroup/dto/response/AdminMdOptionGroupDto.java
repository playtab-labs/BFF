package com.playtab.bff.admin.mdoptiongroup.dto.response;

import java.util.Map;

public record AdminMdOptionGroupDto(
        Long id,
        Long mdItemId,
        Map<String, String> name,
        int displayOrder,
        String createdAt,
        String updatedAt
) {}
