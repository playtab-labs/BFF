package com.playtab.bff.lineup.service;

import com.google.protobuf.Timestamp;
import com.playtab.bff.grpc.client.LineupGrpcClient;
import com.playtab.bff.lineup.dto.output.*;
import com.playtab.lineupservice.grpc.proto.*;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class LineupFacade {

    private final LineupGrpcClient lineupGrpcClient;

    public LineupFacade(LineupGrpcClient lineupGrpcClient) {
        this.lineupGrpcClient = lineupGrpcClient;
    }

    public List<PerformerDto> getPerformers(Boolean activeOnly, String stageName) {
        boolean resolvedActiveOnly = activeOnly == null ? true : activeOnly;

        GetPerformersRequest.Builder builder = GetPerformersRequest.newBuilder()
                .setActiveOnly(resolvedActiveOnly);
        if (stageName != null) {
            builder.setStageName(stageName);
        }

        GetPerformersResponse response = lineupGrpcClient.getPerformers(builder.build());
        return response.getPerformersList().stream()
                .map(this::toPerformerDto)
                .toList();
    }


    public List<PerformanceScheduleDto> getSchedulesByDay(int dayNumber, String stageName) {
        GetSchedulesByDayRequest.Builder builder = GetSchedulesByDayRequest.newBuilder()
                .setDayNumber(dayNumber);
        if (stageName != null) {
            builder.setStageName(stageName);
        }

        GetSchedulesByDayResponse response = lineupGrpcClient.getSchedulesByDay(builder.build());
        return response.getSchedulesList().stream()
                .map(this::toPerformanceScheduleDto)
                .toList();
    }

    public FavoriteDto addFavorite(long performerId) {
        AddFavoriteRequest request = AddFavoriteRequest.newBuilder()
                .setPerformerId(performerId)
                .build();

        AddFavoriteResponse response = lineupGrpcClient.addFavorite(request);
        return toFavoriteDto(response.getFavorite());
    }

    public boolean removeFavorite(long performerId) {
        RemoveFavoriteRequest request = RemoveFavoriteRequest.newBuilder()
                .setPerformerId(performerId)
                .build();

        RemoveFavoriteResponse response = lineupGrpcClient.removeFavorite(request);
        return response.getSuccess();
    }

    public List<PerformerDto> getMyFavorites() {
        GetFavoritesResponse response = lineupGrpcClient.getFavorites();
        return response.getPerformersList().stream()
                .map(this::toPerformerDto)
                .toList();
    }

    // ── Proto → DTO 변환 ──

    private PerformerDto toPerformerDto(Performer proto) {
        PerformerDto dto = new PerformerDto();
        dto.setId(proto.getId());
        dto.setName(toLocalizedMap(proto.getName()));
        dto.setDescription(toLocalizedMap(proto.getDescription()));
        dto.setImageUrl(proto.getImageUrl());
        dto.setActive(proto.getIsActive());
        dto.setFavorited(proto.getIsFavorited());
        dto.setCreatedAt(toIsoString(proto.getCreatedAt()));
        dto.setUpdatedAt(toIsoString(proto.getUpdatedAt()));
        return dto;
    }

    private StageDto toStageDto(Stage proto) {
        StageDto dto = new StageDto();
        dto.setId(proto.getId());
        dto.setName(toLocalizedMap(proto.getName()));
        dto.setLocationDesc(toLocalizedMap(proto.getLocationDesc()));
        dto.setDisplayOrder(proto.getDisplayOrder());
        return dto;
    }

    private FestivalDayDto toFestivalDayDto(FestivalDay proto) {
        FestivalDayDto dto = new FestivalDayDto();
        dto.setId(proto.getId());
        dto.setDayNumber(proto.getDayNumber());
        dto.setEventDate(proto.getEventDate());
        return dto;
    }

    private PerformanceDurationDto toPerformanceDurationDto(PerformanceDuration proto) {
        PerformanceDurationDto dto = new PerformanceDurationDto();
        dto.setDurationMinutes(proto.getDurationMinutes());
        dto.setDurationLabel(proto.getDurationLabel());
        return dto;
    }

    private PerformanceScheduleDto toPerformanceScheduleDto(PerformanceSchedule proto) {
        PerformanceScheduleDto dto = new PerformanceScheduleDto();
        dto.setId(proto.getId());
        dto.setPerformer(toPerformerDto(proto.getPerformer()));
        dto.setStage(toStageDto(proto.getStage()));
        dto.setFestivalDay(toFestivalDayDto(proto.getFestivalDay()));
        dto.setStartAt(toIsoString(proto.getStartAt()));
        dto.setEndAt(toIsoString(proto.getEndAt()));
        dto.setStatus(toScheduleStatus(proto.getStatus()));
        dto.setDuration(toPerformanceDurationDto(proto.getDuration()));
        return dto;
    }

    private FavoriteDto toFavoriteDto(Favorite proto) {
        FavoriteDto dto = new FavoriteDto();
        dto.setId(proto.getId());
        dto.setPerformerId(proto.getPerformerId());
        dto.setCreatedAt(toIsoString(proto.getCreatedAt()));
        return dto;
    }

    private String toScheduleStatus(ScheduleStatusProto proto) {
        return switch (proto) {
            case SCHEDULE_STATUS_PROTO_SCHEDULED -> "SCHEDULED";
            case SCHEDULE_STATUS_PROTO_CANCELLED -> "CANCELLED";
            case SCHEDULE_STATUS_PROTO_COMPLETED -> "COMPLETED";
            default -> "SCHEDULED";
        };
    }

    private Map<String, String> toLocalizedMap(LocalizedText text) {
        if (text == null) {
            return Map.of();
        }
        return text.getValuesMap();
    }

    private String toIsoString(Timestamp timestamp) {
        if (timestamp == null || (timestamp.getSeconds() == 0 && timestamp.getNanos() == 0)) {
            return null;
        }
        return Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos()).toString();
    }
}
