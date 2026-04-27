package com.playtab.bff.auth.dto.request;

public class SendPasswordResetCodeRequestDto {

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
