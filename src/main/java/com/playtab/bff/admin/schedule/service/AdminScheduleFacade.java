package com.playtab.bff.admin.schedule.service;

import com.playtab.bff.admin.schedule.dto.request.AdminScheduleRequestDto;
import com.playtab.bff.admin.schedule.dto.response.AdminScheduleDto;
import com.playtab.bff.grpc.client.LineupGrpcClient;
import com.playtab.lineupservice.grpc.proto.AdminCreateScheduleRequest;
import com.playtab.lineupservice.grpc.proto.AdminDeleteScheduleRequest;
import com.playtab.lineupservice.grpc.proto.AdminUpdateScheduleRequest;
import com.playtab.lineupservice.grpc.proto.PerformanceSchedule;
import com.playtab.lineupservice.grpc.proto.ScheduleStatusProto;
import java.time.Instant;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class AdminScheduleFacade {

    private final LineupGrpcClient lineupGrpcClient;

    public AdminScheduleFacade(LineupGrpcClient lineupGrpcClient) {
        this.lineupGrpcClient = lineupGrpcClient;
    }

    public AdminScheduleDto createSchedule(AdminScheduleRequestDto request) {
        AdminCreateScheduleRequest grpcRequest = AdminCreateScheduleRequest.newBuilder()
                .setPerformerId(request.performerId())
                .setStageId(request.stageId())
                .setFestivalDayId(request.festivalDayId())
                .setStartAt(Objects.requireNonNullElse(request.startAt(), ""))
                .setEndAt(Objects.requireNonNullElse(request.endAt(), ""))
                .setStatus(toStatusProto(request.status()))
                .build();
        return toDto(lineupGrpcClient.adminCreateSchedule(grpcRequest).getSchedule());
    }

    public AdminScheduleDto updateSchedule(Long id, AdminScheduleRequestDto request) {
        AdminUpdateScheduleRequest grpcRequest = AdminUpdateScheduleRequest.newBuilder()
                .setId(id)
                .setPerformerId(request.performerId())
                .setStageId(request.stageId())
                .setFestivalDayId(request.festivalDayId())
                .setStartAt(Objects.requireNonNullElse(request.startAt(), ""))
                .setEndAt(Objects.requireNonNullElse(request.endAt(), ""))
                .setStatus(toStatusProto(request.status()))
                .build();
        return toDto(lineupGrpcClient.adminUpdateSchedule(grpcRequest).getSchedule());
    }

    public void deleteSchedule(Long id) {
        lineupGrpcClient.adminDeleteSchedule(
                AdminDeleteScheduleRequest.newBuilder().setId(id).build()
        );
    }

    private AdminScheduleDto toDto(PerformanceSchedule schedule) {
        String performerName = schedule.hasPerformer()
                ? schedule.getPerformer().getName().getValuesMap().toString()
                : "";
        String stageName = schedule.hasStage()
                ? schedule.getStage().getName().getValuesMap().toString()
                : "";
        return new AdminScheduleDto(
                schedule.getId(),
                schedule.hasPerformer() ? schedule.getPerformer().getId() : null,
                performerName,
                schedule.hasStage() ? schedule.getStage().getId() : null,
                stageName,
                schedule.getStartAt().toString(),
                schedule.getEndAt().toString(),
                schedule.getStatus().name(),
                Instant.ofEpochSecond(schedule.getCreatedAt().getSeconds(), schedule.getCreatedAt().getNanos()).toString(),
                Instant.ofEpochSecond(schedule.getUpdatedAt().getSeconds(), schedule.getUpdatedAt().getNanos()).toString()
        );
    }

    private ScheduleStatusProto toStatusProto(String status) {
        if (status == null) {
            return ScheduleStatusProto.SCHEDULE_STATUS_PROTO_SCHEDULED;
        }
        return switch (status.toUpperCase()) {
            case "CANCELLED" -> ScheduleStatusProto.SCHEDULE_STATUS_PROTO_CANCELLED;
            case "COMPLETED" -> ScheduleStatusProto.SCHEDULE_STATUS_PROTO_COMPLETED;
            default -> ScheduleStatusProto.SCHEDULE_STATUS_PROTO_SCHEDULED;
        };
    }
}
