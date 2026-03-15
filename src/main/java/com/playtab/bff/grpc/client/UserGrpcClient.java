package com.playtab.bff.grpc.client;

import com.playtab.bff.config.GrpcProperties;
import com.playtab.userservice.proto.v1.*;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class UserGrpcClient {

    private final long deadlineSeconds;

    private final UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    public UserGrpcClient(
            UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub,
            GrpcProperties grpcProperties
    ) {
        this.userServiceBlockingStub = userServiceBlockingStub;
        this.deadlineSeconds = grpcProperties.getDeadlineSeconds();
    }

    public UserProfileResponse getMyProfile() {
        return userServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .getMyProfile(GetMyProfileRequest.newBuilder().build());
    }

    public UserSettingsResponse getMySettings() {
        return userServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .getMySettings(GetMySettingsRequest.newBuilder().build());
    }

    public UserProfileResponse updateMyProfile(UpdateMyProfileRequest request) {
        return userServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .updateMyProfile(request);
    }

    public UserSettingsResponse updateMySettings(UpdateMySettingsRequest request) {
        return userServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .updateMySettings(request);
    }

    public UpdateConsentsResponse updateConsents(UpdateConsentsRequest request) {
        return userServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .updateConsents(request);
    }

    public VerifyAdultResponse verifyAdult(boolean isAdult) {
        VerifyAdultRequest request = VerifyAdultRequest.newBuilder()
                .setIsAdult(isAdult)
                .build();

        return userServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .verifyAdult(request);
    }
}