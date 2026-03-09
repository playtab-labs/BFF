package com.playtab.bff.auth.dto.response;

public class SuccessResponseDto {

    private boolean success;

    public SuccessResponseDto() {
    }

    public SuccessResponseDto(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}