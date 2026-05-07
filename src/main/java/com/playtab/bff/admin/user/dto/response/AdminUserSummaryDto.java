package com.playtab.bff.admin.user.dto.response;

public record AdminUserSummaryDto(
        String identityId,
        String profileId,
        String email,
        String name,
        String role,
        String status,
        String createdAt
) {}
