package com.playtab.bff.auth.dto.response;

public class AuthTokensResponseDto {

    private String accessToken;
    private String refreshToken;
    private long accessExpiresInSeconds;
    private long refreshExpiresInSeconds;
    private boolean profileCompleted;
    private boolean requiredConsentsCompleted;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public long getAccessExpiresInSeconds() {
        return accessExpiresInSeconds;
    }

    public void setAccessExpiresInSeconds(long accessExpiresInSeconds) {
        this.accessExpiresInSeconds = accessExpiresInSeconds;
    }

    public long getRefreshExpiresInSeconds() {
        return refreshExpiresInSeconds;
    }

    public void setRefreshExpiresInSeconds(long refreshExpiresInSeconds) {
        this.refreshExpiresInSeconds = refreshExpiresInSeconds;
    }

    public boolean isProfileCompleted() {
        return profileCompleted;
    }

    public void setProfileCompleted(boolean profileCompleted) {
        this.profileCompleted = profileCompleted;
    }

    public boolean isRequiredConsentsCompleted() {
        return requiredConsentsCompleted;
    }

    public void setRequiredConsentsCompleted(boolean requiredConsentsCompleted) {
        this.requiredConsentsCompleted = requiredConsentsCompleted;
    }
}