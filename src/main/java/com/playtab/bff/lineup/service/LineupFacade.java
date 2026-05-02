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

    public List<PerformerDto> getPerformers(Boolean activeOnly, Long stageId, String locale) {
        GetPerformersRequest.Builder builder = GetPerformersRequest.newBuilder();
        if (activeOnly != null) {
            builder.setActiveOnly(activeOnly);
        }
        if (stageId != null) {
            builder.setStageId(stageId);
        }
        if (locale != null) {
            builder.setLocale(locale);
        }

        GetPerformersResponse response = lineupGrpcClient.getPerformers(builder.build());
        return response.getPerformersList().stream()
                .map(proto -> toPerformerDto(proto, locale))
                .toList();
    }

    public List<StageScheduleDto> getSchedulesByDay(long dayId, Long stageId, String locale) {
        GetSchedulesByDayRequest.Builder builder = GetSchedulesByDayRequest.newBuilder()
                .setDayId(dayId);
        if (stageId != null) {
            builder.setStageId(stageId);
        }
        if (locale != null) {
            builder.setLocale(locale);
        }

        GetSchedulesByDayResponse response = lineupGrpcClient.getSchedulesByDay(builder.build());
        return response.getStagesList().stream()
                .map(stage -> toStageScheduleDto(stage, locale))
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
                .map(proto -> toPerformerDto(proto, null))
                .toList();
    }

    public List<FestivalDayDto> getFestivalDays() {
        GetFestivalDaysResponse response = lineupGrpcClient.getFestivalDays();
        return response.getFestivalDaysList().stream()
                .map(this::toFestivalDayDto)
                .toList();
    }

    public List<PerformersByDayDto> getPerformersByDay(String locale) {
        GetPerformersByDayRequest.Builder builder = GetPerformersByDayRequest.newBuilder();
        if (locale != null) {
            builder.setLocale(locale);
        }

        GetPerformersByDayResponse response = lineupGrpcClient.getPerformersByDay(builder.build());
        return response.getDaysList().stream()
                .map(dayProto -> toPerformersByDayDto(dayProto, locale))
                .toList();
    }

    // ── Proto → DTO 변환 ──

    private PerformerDto toPerformerDto(Performer proto, String locale) {
        PerformerDto dto = new PerformerDto();
        dto.setId(proto.getId());
        dto.setName(resolveLocalized(proto.getName(), locale));
        dto.setDescription(resolveLocalized(proto.getDescription(), locale));
        dto.setImageUrl(proto.getImageUrl());
        dto.setActive(proto.getIsActive());
        dto.setFavorited(proto.getIsFavorited());
        dto.setStageNames(proto.getStageNamesList().stream()
                .map(text -> resolveLocalized(text, locale))
                .toList());
        dto.setCreatedAt(toIsoString(proto.getCreatedAt()));
        dto.setUpdatedAt(toIsoString(proto.getUpdatedAt()));
        return dto;
    }

    private PerformersByDayDto toPerformersByDayDto(PerformersByDay proto, String locale) {
        PerformersByDayDto dto = new PerformersByDayDto();
        dto.setDay(toFestivalDayDto(proto.getFestivalDay()));
        dto.setPerformers(proto.getPerformersList().stream()
                .map(p -> toPerformerDto(p, locale))
                .toList());
        return dto;
    }

    private StageDto toStageDto(Stage proto, String locale) {
        StageDto dto = new StageDto();
        dto.setId(proto.getId());
        dto.setName(resolveLocalized(proto.getName(), locale));
        dto.setLocationDesc(resolveLocalized(proto.getLocationDesc(), locale));
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

    private ArtistScheduleDto toArtistScheduleDto(ArtistSchedule proto, String locale) {
        ArtistScheduleDto dto = new ArtistScheduleDto();
        dto.setScheduleId(proto.getScheduleId());
        dto.setPerformer(toPerformerDto(proto.getPerformer(), locale));
        dto.setStartAt(toIsoString(proto.getStartAt()));
        dto.setEndAt(toIsoString(proto.getEndAt()));
        dto.setStatus(toScheduleStatus(proto.getStatus()));
        dto.setDuration(toPerformanceDurationDto(proto.getDuration()));
        return dto;
    }

    private StageScheduleDto toStageScheduleDto(StageSchedule proto, String locale) {
        StageScheduleDto dto = new StageScheduleDto();
        dto.setStage(toStageDto(proto.getStage(), locale));
        dto.setArtists(proto.getArtistsList().stream()
                .map(artist -> toArtistScheduleDto(artist, locale))
                .toList());
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

    private Map<String, String> resolveLocalized(LocalizedText text, String locale) {
        if (text == null) {
            return Map.of();
        }
        Map<String, String> valuesMap = text.getValuesMap();
        if (locale != null && valuesMap.containsKey(locale)) {
            return Map.of(locale, valuesMap.get(locale));
        }
        return valuesMap;
    }

    private String toIsoString(Timestamp timestamp) {
        if (timestamp == null || (timestamp.getSeconds() == 0 && timestamp.getNanos() == 0)) {
            return null;
        }
        return Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos()).toString();
    }
}
