package com.playtab.bff.auth.dto.request;

import java.util.List;

public class LoginWithSocialRequestDto {

    private CredentialTypeDto type;
    private String idToken;
    private String accessToken;
    private String deviceFingerprint;
    private List<ConsentRequestDto> consents;

    public CredentialTypeDto getType() {
        return type;
    }

    public void setType(CredentialTypeDto type) {
        this.type = type;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getDeviceFingerprint() {
        return deviceFingerprint;
    }

    public void setDeviceFingerprint(String deviceFingerprint) {
        this.deviceFingerprint = deviceFingerprint;
    }

    public List<ConsentRequestDto> getConsents() {
        return consents;
    }

    public void setConsents(List<ConsentRequestDto> consents) {
        this.consents = consents;
    }
}