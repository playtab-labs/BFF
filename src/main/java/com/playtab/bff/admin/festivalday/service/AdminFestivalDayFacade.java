package com.playtab.bff.admin.festivalday.service;

import com.playtab.bff.admin.festivalday.dto.request.AdminFestivalDayRequestDto;
import com.playtab.bff.admin.festivalday.dto.response.AdminFestivalDayDto;
import com.playtab.bff.grpc.client.LineupGrpcClient;
import com.playtab.lineupservice.grpc.proto.AdminCreateFestivalDayRequest;
import com.playtab.lineupservice.grpc.proto.AdminDeleteFestivalDayRequest;
import com.playtab.lineupservice.grpc.proto.AdminUpdateFestivalDayRequest;
import com.playtab.lineupservice.grpc.proto.FestivalDay;
import java.time.Instant;
import org.springframework.stereotype.Service;

@Service
public class AdminFestivalDayFacade {

    private final LineupGrpcClient lineupGrpcClient;

    public AdminFestivalDayFacade(LineupGrpcClient lineupGrpcClient) {
        this.lineupGrpcClient = lineupGrpcClient;
    }

    public AdminFestivalDayDto createFestivalDay(AdminFestivalDayRequestDto request) {
        AdminCreateFestivalDayRequest grpcRequest = AdminCreateFestivalDayRequest.newBuilder()
                .setDayNumber(request.dayNumber())
                .setEventDate(request.eventDate())
                .build();
        return toDto(lineupGrpcClient.adminCreateFestivalDay(grpcRequest).getFestivalDay());
    }

    public AdminFestivalDayDto updateFestivalDay(Long id, AdminFestivalDayRequestDto request) {
        AdminUpdateFestivalDayRequest grpcRequest = AdminUpdateFestivalDayRequest.newBuilder()
                .setId(id)
                .setDayNumber(request.dayNumber())
                .setEventDate(request.eventDate())
                .build();
        return toDto(lineupGrpcClient.adminUpdateFestivalDay(grpcRequest).getFestivalDay());
    }

    public void deleteFestivalDay(Long id) {
        lineupGrpcClient.adminDeleteFestivalDay(
                AdminDeleteFestivalDayRequest.newBuilder().setId(id).build()
        );
    }

    private AdminFestivalDayDto toDto(FestivalDay day) {
        return new AdminFestivalDayDto(
                day.getId(),
                day.getDayNumber(),
                day.getEventDate(),
                Instant.ofEpochSecond(day.getCreatedAt().getSeconds(), day.getCreatedAt().getNanos()).toString(),
                Instant.ofEpochSecond(day.getUpdatedAt().getSeconds(), day.getUpdatedAt().getNanos()).toString()
        );
    }
}
