package com.playtab.bff.admin.festivalday.dto.response;

public record AdminFestivalDayDto(
        Long id,
        int dayNumber,
        String eventDate,
        String createdAt,
        String updatedAt
) {}
