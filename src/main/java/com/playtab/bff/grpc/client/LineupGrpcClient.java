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
}
