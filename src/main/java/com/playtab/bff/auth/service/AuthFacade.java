package com.playtab.bff.auth.service;

import com.google.protobuf.Timestamp;
import com.playtab.bff.auth.dto.request.*;
import com.playtab.bff.auth.dto.response.AuthTokensResponseDto;
import com.playtab.bff.auth.dto.response.SendEmailVerificationCodeResponseDto;
import com.playtab.bff.auth.dto.response.SignUpResponseDto;
import com.playtab.bff.auth.dto.response.SuccessResponseDto;
import com.playtab.bff.auth.dto.response.VerifyEmailCodeResponseDto;
import com.playtab.bff.common.util.ClientContextExtractor;
import com.playtab.bff.grpc.client.AuthGrpcClient;
import com.playtab.userservice.proto.v1.*;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AuthFacade {

    private final AuthGrpcClient authGrpcClient;
    private final ClientContextExtractor clientContextExtractor;

    public AuthFacade(
            AuthGrpcClient authGrpcClient,
            ClientContextExtractor clientContextExtractor
    ) {
        this.authGrpcClient = authGrpcClient;
        this.clientContextExtractor = clientContextExtractor;
    }

    public AuthTokensResponseDto loginWithEmail(
            LoginWithEmailRequestDto requestDto,
            HttpServletRequest httpServletRequest
    ) {
        LoginWithEmailRequest request = LoginWithEmailRequest.newBuilder()
                .setEmail(nullToEmpty(requestDto.getEmail()))
                .setPassword(nullToEmpty(requestDto.getPassword()))
                .setClient(clientContextExtractor.extract(
                        httpServletRequest,
                        requestDto.getDeviceFingerprint()
                ))
                .build();

        AuthTokensResponse response = authGrpcClient.loginWithEmail(request);
        return toAuthTokensResponseDto(response);
    }

    public AuthTokensResponseDto refreshTokens(
            RefreshTokensRequestDto requestDto,
            HttpServletRequest httpServletRequest
    ) {
        RefreshTokensRequest request = RefreshTokensRequest.newBuilder()
                .setRefreshToken(nullToEmpty(requestDto.getRefreshToken()))
                .setClient(clientContextExtractor.extract(
                        httpServletRequest,
                        requestDto.getDeviceFingerprint()
                ))
                .build();

        AuthTokensResponse response = authGrpcClient.refreshTokens(request);
        return toAuthTokensResponseDto(response);
    }

    public SuccessResponseDto logout(LogoutRequestDto requestDto) {
        LogoutResponse response = authGrpcClient.logout(nullToEmpty(requestDto.getRefreshToken()));
        return new SuccessResponseDto(response.getSuccess());
    }

    public SignUpResponseDto signUpWithEmail(SignUpWithEmailRequestDto requestDto) {
        SignUpWithEmailRequest request = SignUpWithEmailRequest.newBuilder()
                .setEmail(nullToEmpty(requestDto.getEmail()))
                .setPassword(nullToEmpty(requestDto.getPassword()))
                .setName(nullToEmpty(requestDto.getName()))
                .setGender(toProtoGender(requestDto.getGender()))
                .setPhoneNumber(nullToEmpty(requestDto.getPhoneNumber()))
                .setBirthDate(nullToEmpty(requestDto.getBirthDate()))
                .setNationality(nullToEmpty(requestDto.getNationality()))
                .setSessionId(nullToEmpty(requestDto.getSessionId()))
                .addAllConsents(toProtoConsents(requestDto.getConsents()))
                .build();

        SignUpResponse response = authGrpcClient.signUpWithEmail(request);

        SignUpResponseDto dto = new SignUpResponseDto();
        dto.setIdentityId(response.getIdentityId());
        dto.setProfileId(response.getProfileId());
        dto.setProfileCompleted(response.getProfileCompleted());
        dto.setCreatedAt(toIsoString(response.getCreatedAt()));
        return dto;
    }

    public SendEmailVerificationCodeResponseDto sendEmailVerificationCode(
            SendEmailVerificationCodeRequestDto requestDto
    ) {
        SendEmailVerificationCodeRequest request = SendEmailVerificationCodeRequest.newBuilder()
                .setEmail(nullToEmpty(requestDto.getEmail()))
                .setSessionId(nullToEmpty(requestDto.getSessionId()))
                .build();

        SendEmailVerificationCodeResponse response =
                authGrpcClient.sendEmailVerificationCode(request);

        SendEmailVerificationCodeResponseDto dto = new SendEmailVerificationCodeResponseDto();
        dto.setSuccess(response.getSuccess());
        dto.setTtlSeconds(response.getTtlSeconds());
        return dto;
    }

    public VerifyEmailCodeResponseDto verifyEmailCode(VerifyEmailCodeRequestDto requestDto) {
        VerifyEmailCodeRequest request = VerifyEmailCodeRequest.newBuilder()
                .setEmail(nullToEmpty(requestDto.getEmail()))
                .setCode(nullToEmpty(requestDto.getCode()))
                .setSessionId(nullToEmpty(requestDto.getSessionId()))
                .build();

        VerifyEmailCodeResponse response = authGrpcClient.verifyEmailCode(request);

        VerifyEmailCodeResponseDto dto = new VerifyEmailCodeResponseDto();
        dto.setSuccess(response.getSuccess());
        dto.setVerified(response.getVerified());
        return dto;
    }

    public AuthTokensResponseDto loginWithSocial(
            LoginWithSocialRequestDto requestDto,
            HttpServletRequest httpServletRequest
    ) {
        LoginWithSocialRequest request = LoginWithSocialRequest.newBuilder()
                .setType(toProtoCredentialType(requestDto.getType()))
                .setIdToken(nullToEmpty(requestDto.getIdToken()))
                .setAccessToken(nullToEmpty(requestDto.getAccessToken()))
                .setClient(clientContextExtractor.extract(
                        httpServletRequest,
                        requestDto.getDeviceFingerprint()
                ))
                .addAllConsents(toProtoConsents(requestDto.getConsents()))
                .build();

        AuthTokensResponse response = authGrpcClient.loginWithSocial(request);
        return toAuthTokensResponseDto(response);
    }

    private AuthTokensResponseDto toAuthTokensResponseDto(AuthTokensResponse response) {
        AuthTokensResponseDto dto = new AuthTokensResponseDto();
        dto.setAccessToken(response.getAccessToken());
        dto.setRefreshToken(response.getRefreshToken());
        dto.setAccessExpiresInSeconds(response.getAccessExpiresInSeconds());
        dto.setRefreshExpiresInSeconds(response.getRefreshExpiresInSeconds());
        dto.setProfileCompleted(response.getProfileCompleted());
        dto.setRequiredConsentsCompleted(response.getRequiredConsentsCompleted());
        return dto;
    }

    private List<ConsentInput> toProtoConsents(List<ConsentRequestDto> consents) {
        if (consents == null || consents.isEmpty()) {
            return Collections.emptyList();
        }

        return consents.stream()
                .map(this::toProtoConsent)
                .toList();
    }

    private ConsentInput toProtoConsent(ConsentRequestDto dto) {
        return ConsentInput.newBuilder()
                .setTermsVersion(nullToEmpty(dto.getTermsVersion()))
                .setType(toProtoConsentType(dto.getType()))
                .setIsAgreed(dto.isAgreed())
                .build();
    }

    private Gender toProtoGender(GenderDto genderDto) {
        if (genderDto == null) {
            return Gender.GENDER_UNSPECIFIED;
        }

        return switch (genderDto) {
            case MALE -> Gender.MALE;
            case FEMALE -> Gender.FEMALE;
            case OTHER -> Gender.OTHER;
        };
    }

    private ConsentType toProtoConsentType(ConsentTypeDto typeDto) {
        if (typeDto == null) {
            return ConsentType.CONSENT_TYPE_UNSPECIFIED;
        }

        return switch (typeDto) {
            case PRIVACY -> ConsentType.PRIVACY;
            case SERVICE -> ConsentType.SERVICE;
            case MARKETING -> ConsentType.MARKETING;
        };
    }

    private CredentialType toProtoCredentialType(CredentialTypeDto typeDto) {
        if (typeDto == null) {
            return CredentialType.CREDENTIAL_TYPE_UNSPECIFIED;
        }

        return switch (typeDto) {
            case GOOGLE -> CredentialType.GOOGLE;
            case KAKAO -> CredentialType.KAKAO;
            case NAVER -> CredentialType.NAVER;
            case APPLE -> CredentialType.APPLE;
        };
    }

    private String toIsoString(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos()).toString();
    }

    private String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
}