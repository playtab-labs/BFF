package com.playtab.bff.user.dto.input;

import com.playtab.bff.user.dto.ConsentTypeDto;

public class ConsentInputDto {

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