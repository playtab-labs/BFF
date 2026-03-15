package com.playtab.bff.user.dto.output;

public class SuccessResultDto {

    private boolean success;

    public SuccessResultDto() {
    }

    public SuccessResultDto(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}