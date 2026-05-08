package com.playtab.bff.admin.schedule.dto.request;

public record AdminScheduleRequestDto(
        Long performerId,
        Long stageId,
        Long festivalDayId,
        String startAt,
        String endAt,
        String status
) {}
