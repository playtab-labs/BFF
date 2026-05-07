package com.playtab.bff.admin.user.dto.response;

public record AdminUserDetailDto(
        String identityId,
        String profileId,
        String email,
        String name,
        String gender,
        String phoneNumber,
        String birthDate,
        boolean isAdult,
        String nationality,
        String role,
        String status,
        String createdAt,
        String updatedAt
) {}
