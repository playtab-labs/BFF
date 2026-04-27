package com.playtab.bff.grpc.client;

import com.playtab.bff.config.GrpcProperties;
import com.playtab.userservice.proto.v1.AuthServiceGrpc;
import com.playtab.userservice.proto.v1.AuthTokensResponse;
import com.playtab.userservice.proto.v1.GetMyAuthSummaryRequest;
import com.playtab.userservice.proto.v1.LoginWithEmailRequest;
import com.playtab.userservice.proto.v1.LoginWithSocialRequest;
import com.playtab.userservice.proto.v1.LogoutRequest;
import com.playtab.userservice.proto.v1.LogoutResponse;
import com.playtab.userservice.proto.v1.MyAuthSummaryResponse;
import com.playtab.userservice.proto.v1.RefreshTokensRequest;
import com.playtab.userservice.proto.v1.SendEmailVerificationCodeRequest;
import com.playtab.userservice.proto.v1.SendEmailVerificationCodeResponse;
import com.playtab.userservice.proto.v1.SignUpResponse;
import com.playtab.userservice.proto.v1.SignUpWithEmailRequest;
import com.playtab.userservice.proto.v1.UserServiceGrpc;
import com.playtab.userservice.proto.v1.ResetPasswordRequest;
import com.playtab.userservice.proto.v1.ResetPasswordResponse;
import com.playtab.userservice.proto.v1.SendPasswordResetCodeRequest;
import com.playtab.userservice.proto.v1.SendPasswordResetCodeResponse;
import com.playtab.userservice.proto.v1.VerifyEmailCodeRequest;
import com.playtab.userservice.proto.v1.VerifyEmailCodeResponse;
import com.playtab.userservice.proto.v1.VerifyPasswordResetCodeRequest;
import com.playtab.userservice.proto.v1.VerifyPasswordResetCodeResponse;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Component;

@Component
public class AuthGrpcClient {

    private final long deadlineSeconds;
    private final AuthServiceGrpc.AuthServiceBlockingStub authServiceBlockingStub;
    private final UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    public AuthGrpcClient(
            AuthServiceGrpc.AuthServiceBlockingStub authServiceBlockingStub,
            UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub,
            GrpcProperties grpcProperties
    ) {
        this.authServiceBlockingStub = authServiceBlockingStub;
        this.userServiceBlockingStub = userServiceBlockingStub;
        this.deadlineSeconds = grpcProperties.getDeadlineSeconds();
    }

    public AuthTokensResponse loginWithEmail(LoginWithEmailRequest request) {
        return authServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .loginWithEmail(request);
    }

    public AuthTokensResponse loginWithSocial(LoginWithSocialRequest request) {
        return authServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .loginWithSocial(request);
    }

    public AuthTokensResponse refreshTokens(RefreshTokensRequest request) {
        return authServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .refreshTokens(request);
    }

    public LogoutResponse logout(String refreshToken) {
        LogoutRequest request = LogoutRequest.newBuilder()
                .setRefreshToken(refreshToken)
                .build();

        return authServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .logout(request);
    }

    public MyAuthSummaryResponse getMyAuthSummary() {
        return authServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .getMyAuthSummary(GetMyAuthSummaryRequest.newBuilder().build());
    }

    public SignUpResponse signUpWithEmail(SignUpWithEmailRequest request) {
        return userServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .signUpWithEmail(request);
    }

    public SendEmailVerificationCodeResponse sendEmailVerificationCode(
            SendEmailVerificationCodeRequest request
    ) {
        return userServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .sendEmailVerificationCode(request);
    }

    public VerifyEmailCodeResponse verifyEmailCode(VerifyEmailCodeRequest request) {
        return userServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .verifyEmailCode(request);
    }

    public SendPasswordResetCodeResponse sendPasswordResetCode(SendPasswordResetCodeRequest request) {
        return userServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .sendPasswordResetCode(request);
    }

    public VerifyPasswordResetCodeResponse verifyPasswordResetCode(VerifyPasswordResetCodeRequest request) {
        return userServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .verifyPasswordResetCode(request);
    }

    public ResetPasswordResponse resetPassword(ResetPasswordRequest request) {
        return userServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .resetPassword(request);
    }
}