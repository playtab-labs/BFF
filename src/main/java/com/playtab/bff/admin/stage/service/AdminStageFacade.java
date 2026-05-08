package com.playtab.bff.admin.stage.service;

import com.playtab.bff.admin.stage.dto.request.AdminStageRequestDto;
import com.playtab.bff.admin.stage.dto.response.AdminStageDto;
import com.playtab.bff.grpc.client.LineupGrpcClient;
import com.playtab.lineupservice.grpc.proto.AdminCreateStageRequest;
import com.playtab.lineupservice.grpc.proto.AdminDeleteStageRequest;
import com.playtab.lineupservice.grpc.proto.AdminUpdateStageRequest;
import com.playtab.lineupservice.grpc.proto.LocalizedText;
import com.playtab.lineupservice.grpc.proto.Stage;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class AdminStageFacade {

    private final LineupGrpcClient lineupGrpcClient;

    public AdminStageFacade(LineupGrpcClient lineupGrpcClient) {
        this.lineupGrpcClient = lineupGrpcClient;
    }

    public AdminStageDto createStage(AdminStageRequestDto request) {
        AdminCreateStageRequest grpcRequest = AdminCreateStageRequest.newBuilder()
                .setName(toLocalizedText(request.name()))
                .setLocationDesc(toLocalizedText(request.locationDesc()))
                .setDisplayOrder(request.displayOrder())
                .build();
        return toDto(lineupGrpcClient.adminCreateStage(grpcRequest).getStage());
    }

    public AdminStageDto updateStage(Long id, AdminStageRequestDto request) {
        AdminUpdateStageRequest grpcRequest = AdminUpdateStageRequest.newBuilder()
                .setId(id)
                .setName(toLocalizedText(request.name()))
                .setLocationDesc(toLocalizedText(request.locationDesc()))
                .setDisplayOrder(request.displayOrder())
                .build();
        return toDto(lineupGrpcClient.adminUpdateStage(grpcRequest).getStage());
    }

    public void deleteStage(Long id) {
        lineupGrpcClient.adminDeleteStage(
                AdminDeleteStageRequest.newBuilder().setId(id).build()
        );
    }

    private AdminStageDto toDto(Stage stage) {
        return new AdminStageDto(
                stage.getId(),
                stage.getName().getValuesMap(),
                stage.getLocationDesc().getValuesMap(),
                stage.getDisplayOrder(),
                Instant.ofEpochSecond(stage.getCreatedAt().getSeconds(), stage.getCreatedAt().getNanos()).toString(),
                Instant.ofEpochSecond(stage.getUpdatedAt().getSeconds(), stage.getUpdatedAt().getNanos()).toString()
        );
    }

    private LocalizedText toLocalizedText(Map<String, String> values) {
        return LocalizedText.newBuilder()
                .putAllValues(Objects.requireNonNullElse(values, Map.of()))
                .build();
    }
}
