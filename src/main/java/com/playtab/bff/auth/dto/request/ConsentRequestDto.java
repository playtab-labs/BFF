package com.playtab.bff.auth.dto.request;

public class ConsentRequestDto {

    private String termsVersion;
    private ConsentTypeDto type;
    private boolean isAgreed;

    public String getTermsVersion() {
        return termsVersion;
    }

    public void setTermsVersion(String termsVersion) {
        this.termsVersion = termsVersion;
    }

    public ConsentTypeDto getType() {
        return type;
    }

    public void setType(ConsentTypeDto type) {
        this.type = type;
    }

    public boolean isAgreed() {
        return isAgreed;
    }

    public void setAgreed(boolean agreed) {
        isAgreed = agreed;
    }
}