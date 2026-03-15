package com.playtab.bff.user.dto.output;

public class VerifyAdultResultDto {

    private boolean success;
    private boolean isAdult;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }
}