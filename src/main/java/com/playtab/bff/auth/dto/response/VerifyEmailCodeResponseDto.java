package com.playtab.bff.auth.dto.response;

public class VerifyEmailCodeResponseDto {

    private boolean success;
    private boolean verified;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}