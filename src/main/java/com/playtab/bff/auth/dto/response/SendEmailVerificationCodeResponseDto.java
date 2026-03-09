package com.playtab.bff.auth.dto.response;

public class SendEmailVerificationCodeResponseDto {

    private boolean success;
    private long ttlSeconds;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public long getTtlSeconds() {
        return ttlSeconds;
    }

    public void setTtlSeconds(long ttlSeconds) {
        this.ttlSeconds = ttlSeconds;
    }
}