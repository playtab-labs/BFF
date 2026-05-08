package com.playtab.bff.admin.performer.service;

import com.playtab.bff.admin.performer.dto.request.AdminPerformerRequestDto;
import com.playtab.bff.admin.performer.dto.response.AdminPerformerDto;
import com.playtab.bff.grpc.client.LineupGrpcClient;
import com.playtab.lineupservice.grpc.proto.AdminCreatePerformerRequest;
import com.playtab.lineupservice.grpc.proto.AdminDeletePerformerRequest;
import com.playtab.lineupservice.grpc.proto.AdminUpdatePerformerRequest;
import com.playtab.lineupservice.grpc.proto.LocalizedText;
import com.playtab.lineupservice.grpc.proto.Performer;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class AdminPerformerFacade {

    private final LineupGrpcClient lineupGrpcClient;

    public AdminPerformerFacade(LineupGrpcClient lineupGrpcClient) {
        this.lineupGrpcClient = lineupGrpcClient;
    }

    public AdminPerformerDto createPerformer(AdminPerformerRequestDto request) {
        AdminCreatePerformerRequest grpcRequest = AdminCreatePerformerRequest.newBuilder()
                .setName(toLocalizedText(request.name()))
                .setDescription(toLocalizedText(request.description()))
                .setImageUrl(Objects.requireNonNullElse(request.imageUrl(), ""))
                .setIsActive(request.isActive())
                .build();
        return toDto(lineupGrpcClient.adminCreatePerformer(grpcRequest).getPerformer());
    }

    public AdminPerformerDto updatePerformer(Long id, AdminPerformerRequestDto request) {
        AdminUpdatePerformerRequest grpcRequest = AdminUpdatePerformerRequest.newBuilder()
                .setId(id)
                .setName(toLocalizedText(request.name()))
                .setDescription(toLocalizedText(request.description()))
                .setImageUrl(Objects.requireNonNullElse(request.imageUrl(), ""))
                .setIsActive(request.isActive())
                .build();
        return toDto(lineupGrpcClient.adminUpdatePerformer(grpcRequest).getPerformer());
    }

    public void deletePerformer(Long id) {
        lineupGrpcClient.adminDeletePerformer(
                AdminDeletePerformerRequest.newBuilder().setId(id).build()
        );
    }

    private AdminPerformerDto toDto(Performer performer) {
        return new AdminPerformerDto(
                performer.getId(),
                performer.getName().getValuesMap(),
                performer.getDescription().getValuesMap(),
                performer.getImageUrl(),
                performer.getIsActive(),
                Instant.ofEpochSecond(performer.getCreatedAt().getSeconds(), performer.getCreatedAt().getNanos()).toString(),
                Instant.ofEpochSecond(performer.getUpdatedAt().getSeconds(), performer.getUpdatedAt().getNanos()).toString()
        );
    }

    private LocalizedText toLocalizedText(Map<String, String> values) {
        return LocalizedText.newBuilder()
                .putAllValues(Objects.requireNonNullElse(values, Map.of()))
                .build();
    }
}
