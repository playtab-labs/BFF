package com.playtab.bff.auth.dto.request;

public class SendEmailVerificationCodeRequestDto {

    private String email;
    private String sessionId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}