package com.playtab.bff.admin.user.dto.response;

import java.util.List;

public record AdminUserListDto(
        List<AdminUserSummaryDto> users,
        long total,
        int page,
        int size
) {}
