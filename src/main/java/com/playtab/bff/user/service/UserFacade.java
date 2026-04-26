package com.playtab.bff.user.service;

import com.google.protobuf.BoolValue;
import com.google.protobuf.StringValue;
import com.google.protobuf.Timestamp;
import com.playtab.bff.grpc.client.AuthGrpcClient;
import com.playtab.bff.grpc.client.UserGrpcClient;
import com.playtab.bff.user.dto.ConsentTypeDto;
import com.playtab.bff.user.dto.GenderDto;
import com.playtab.bff.user.dto.input.ChangeMyPasswordInput;
import com.playtab.bff.user.dto.input.ConsentInputDto;
import com.playtab.bff.user.dto.input.UpdateMyProfileInput;
import com.playtab.bff.user.dto.input.UpdateMySettingsInput;
import com.playtab.bff.user.dto.input.WithdrawMyAccountInput;
import com.playtab.bff.user.dto.output.MyAuthSummaryDto;
import com.playtab.bff.user.dto.output.SuccessResultDto;
import com.playtab.bff.user.dto.output.UserProfileDto;
import com.playtab.bff.user.dto.output.UserSettingsDto;
import com.playtab.bff.user.dto.output.VerifyAdultResultDto;
import com.playtab.userservice.proto.v1.*;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserFacade {

    private final UserGrpcClient userGrpcClient;
    private final AuthGrpcClient authGrpcClient;

    public UserFacade(UserGrpcClient userGrpcClient, AuthGrpcClient authGrpcClient) {
        this.userGrpcClient = userGrpcClient;
        this.authGrpcClient = authGrpcClient;
    }

    public UserProfileDto getMyProfile() {
        UserProfileResponse response = userGrpcClient.getMyProfile();
        return toUserProfileDto(response);
    }

    public UserSettingsDto getMySettings() {
        UserSettingsResponse response = userGrpcClient.getMySettings();
        return toUserSettingsDto(response);
    }

    public MyAuthSummaryDto getMyAuthSummary() {
        MyAuthSummaryResponse response = authGrpcClient.getMyAuthSummary();
        return toMyAuthSummaryDto(response);
    }

    public UserProfileDto updateMyProfile(UpdateMyProfileInput input) {
        UpdateMyProfileRequest request = UpdateMyProfileRequest.newBuilder()
                .setName(nullToEmpty(input.getName()))
                .setGender(toProtoGender(input.getGender()))
                .setPhoneNumber(nullToEmpty(input.getPhoneNumber()))
                .setBirthDate(nullToEmpty(input.getBirthDate()))
                .setNationality(nullToEmpty(input.getNationality()))
                .build();

        UserProfileResponse response = userGrpcClient.updateMyProfile(request);
        return toUserProfileDto(response);
    }

    public UserSettingsDto updateMySettings(UpdateMySettingsInput input) {
        UpdateMySettingsRequest.Builder builder = UpdateMySettingsRequest.newBuilder();

        if (input.getLocale() != null) {
            builder.setLocale(StringValue.of(input.getLocale()));
        }
        if (input.getPushEnabled() != null) {
            builder.setPushEnabled(BoolValue.of(input.getPushEnabled()));
        }
        if (input.getEmailNotificationsEnabled() != null) {
            builder.setEmailNotificationsEnabled(BoolValue.of(input.getEmailNotificationsEnabled()));
        }

        UserSettingsResponse response = userGrpcClient.updateMySettings(builder.build());
        return toUserSettingsDto(response);
    }

    public SuccessResultDto updateConsents(List<ConsentInputDto> consents) {
        UpdateConsentsRequest request = UpdateConsentsRequest.newBuilder()
                .addAllConsents(toProtoConsents(consents))
                .build();

        UpdateConsentsResponse response = userGrpcClient.updateConsents(request);
        return new SuccessResultDto(response.getSuccess());
    }

    public VerifyAdultResultDto verifyAdult(boolean isAdult) {
        VerifyAdultResponse response = userGrpcClient.verifyAdult(isAdult);

        VerifyAdultResultDto dto = new VerifyAdultResultDto();
        dto.setSuccess(response.getSuccess());
        dto.setAdult(response.getIsAdult());
        return dto;
    }

    public SuccessResultDto changeMyPassword(ChangeMyPasswordInput input) {
        ChangeMyPasswordRequest request = ChangeMyPasswordRequest.newBuilder()
                .setCurrentPassword(nullToEmpty(input.getCurrentPassword()))
                .setNewPassword(nullToEmpty(input.getNewPassword()))
                .build();

        ChangeMyPasswordResponse response = userGrpcClient.changeMyPassword(request);
        return new SuccessResultDto(response.getSuccess());
    }

    public SuccessResultDto withdrawMyAccount(WithdrawMyAccountInput input) {
        WithdrawMyAccountRequest request = WithdrawMyAccountRequest.newBuilder()
                .setRefreshToken(nullToEmpty(input.getRefreshToken()))
                .build();

        WithdrawMyAccountResponse response = userGrpcClient.withdrawMyAccount(request);
        return new SuccessResultDto(response.getSuccess());
    }

    // ── Proto → DTO 변환 ──

    private UserProfileDto toUserProfileDto(UserProfileResponse response) {
        UserProfileDto dto = new UserProfileDto();
        dto.setIdentityId(response.getIdentityId());
        dto.setProfileId(response.getProfileId());
        dto.setEmail(response.getEmail());
        dto.setName(response.getName());
        dto.setGender(fromProtoGender(response.getGender()));
        dto.setPhoneNumber(response.getPhoneNumber());
        dto.setBirthDate(response.getBirthDate());
        dto.setAdult(response.getIsAdult());
        dto.setNationality(response.getNationality());
        dto.setUpdatedAt(toIsoString(response.getUpdatedAt()));
        return dto;
    }

    private UserSettingsDto toUserSettingsDto(UserSettingsResponse response) {
        UserSettingsDto dto = new UserSettingsDto();
        dto.setIdentityId(response.getIdentityId());
        dto.setLocale(response.getLocale());
        dto.setPushEnabled(response.getPushEnabled());
        dto.setEmailNotificationsEnabled(response.getEmailNotificationsEnabled());
        dto.setMarketingAgreed(response.getMarketingAgreed());
        dto.setMarketingTermsVersion(response.getMarketingTermsVersion());
        return dto;
    }

    private MyAuthSummaryDto toMyAuthSummaryDto(MyAuthSummaryResponse response) {
        MyAuthSummaryDto dto = new MyAuthSummaryDto();
        dto.setIdentityId(response.getIdentityId());
        dto.setRole(response.getRole());
        dto.setStatus(response.getStatus());
        dto.setCreatedAt(toIsoString(response.getCreatedAt()));
        return dto;
    }

    // ── DTO → Proto 변환 ──

    private List<com.playtab.userservice.proto.v1.ConsentInput> toProtoConsents(
            List<ConsentInputDto> consents
    ) {
        if (consents == null || consents.isEmpty()) {
            return Collections.emptyList();
        }
        return consents.stream()
                .map(this::toProtoConsent)
                .toList();
    }

    private com.playtab.userservice.proto.v1.ConsentInput toProtoConsent(ConsentInputDto dto) {
        return com.playtab.userservice.proto.v1.ConsentInput.newBuilder()
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

    private GenderDto fromProtoGender(Gender gender) {
        return switch (gender) {
            case MALE -> GenderDto.MALE;
            case FEMALE -> GenderDto.FEMALE;
            case OTHER -> GenderDto.OTHER;
            default -> null;
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

    private String toIsoString(Timestamp timestamp) {
        if (timestamp == null || (timestamp.getSeconds() == 0 && timestamp.getNanos() == 0)) {
            return null;
        }
        return Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos()).toString();
    }

    private String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
}