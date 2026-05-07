package com.playtab.bff.admin.mdoptiongroup.dto.request;

import java.util.Map;

public record AdminMdOptionGroupRequestDto(
        Long mdItemId,
        Map<String, String> name,
        int displayOrder
) {}
