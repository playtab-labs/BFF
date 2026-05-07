package com.playtab.bff.admin.schedule.dto.response;

public record AdminScheduleDto(
        Long id,
        Long performerId,
        String performerName,
        Long stageId,
        String stageName,
        String startAt,
        String endAt,
        String status,
        String createdAt,
        String updatedAt
) {}
