package com.playtab.bff.grpc.client;

import com.playtab.bff.config.LineupServiceProperties;
import com.playtab.lineupservice.grpc.proto.*;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Component;

@Component
public class LineupGrpcClient {

    private final long deadlineSeconds;
    private final LineupServiceGrpc.LineupServiceBlockingStub lineupServiceBlockingStub;

    public LineupGrpcClient(
            LineupServiceGrpc.LineupServiceBlockingStub lineupServiceBlockingStub,
            LineupServiceProperties properties
    ) {
        this.lineupServiceBlockingStub = lineupServiceBlockingStub;
        this.deadlineSeconds = properties.getDeadlineSeconds();
    }

    public GetPerformersResponse getPerformers(GetPerformersRequest request) {
        return lineupServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .getPerformers(request);
    }

    public GetSchedulesByDayResponse getSchedulesByDay(GetSchedulesByDayRequest request) {
        return lineupServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .getSchedulesByDay(request);
    }

    public AddFavoriteResponse addFavorite(AddFavoriteRequest request) {
        return lineupServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .addFavorite(request);
    }

    public RemoveFavoriteResponse removeFavorite(RemoveFavoriteRequest request) {
        return lineupServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .removeFavorite(request);
    }

    public GetFavoritesResponse getFavorites() {
        return lineupServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .getFavorites(GetFavoritesRequest.newBuilder().build());
    }

    public GetFestivalDaysResponse getFestivalDays() {
        return lineupServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .getFestivalDays(GetFestivalDaysRequest.newBuilder().build());
    }

    public GetPerformersByDayResponse getPerformersByDay(GetPerformersByDayRequest request) {
        return lineupServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .getPerformersByDay(request);
    }

    public AdminCreateFestivalDayResponse adminCreateFestivalDay(AdminCreateFestivalDayRequest request) {
        return lineupServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .adminCreateFestivalDay(request);
    }

    public AdminUpdateFestivalDayResponse adminUpdateFestivalDay(AdminUpdateFestivalDayRequest request) {
        return lineupServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .adminUpdateFestivalDay(request);
    }

    public AdminDeleteFestivalDayResponse adminDeleteFestivalDay(AdminDeleteFestivalDayRequest request) {
        return lineupServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .adminDeleteFestivalDay(request);
    }

    public AdminCreateStageResponse adminCreateStage(AdminCreateStageRequest request) {
        return lineupServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .adminCreateStage(request);
    }

    public AdminUpdateStageResponse adminUpdateStage(AdminUpdateStageRequest request) {
        return lineupServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .adminUpdateStage(request);
    }

    public AdminDeleteStageResponse adminDeleteStage(AdminDeleteStageRequest request) {
        return lineupServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .adminDeleteStage(request);
    }

    public AdminCreatePerformerResponse adminCreatePerformer(AdminCreatePerformerRequest request) {
        return lineupServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .adminCreatePerformer(request);
    }

    public AdminUpdatePerformerResponse adminUpdatePerformer(AdminUpdatePerformerRequest request) {
        return lineupServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .adminUpdatePerformer(request);
    }

    public AdminDeletePerformerResponse adminDeletePerformer(AdminDeletePerformerRequest request) {
        return lineupServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .adminDeletePerformer(request);
    }

    public AdminCreateScheduleResponse adminCreateSchedule(AdminCreateScheduleRequest request) {
        return lineupServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .adminCreateSchedule(request);
    }

    public AdminUpdateScheduleResponse adminUpdateSchedule(AdminUpdateScheduleRequest request) {
        return lineupServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .adminUpdateSchedule(request);
    }

    public AdminDeleteScheduleResponse adminDeleteSchedule(AdminDeleteScheduleRequest request) {
        return lineupServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .adminDeleteSchedule(request);
    }
}
