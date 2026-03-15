package com.playtab.bff.user.dto.output;

public class UserSettingsDto {

    private String identityId;
    private String locale;
    private boolean pushEnabled;
    private boolean emailNotificationsEnabled;
    private boolean marketingAgreed;
    private String marketingTermsVersion;

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public boolean isPushEnabled() {
        return pushEnabled;
    }

    public void setPushEnabled(boolean pushEnabled) {
        this.pushEnabled = pushEnabled;
    }

    public boolean isEmailNotificationsEnabled() {
        return emailNotificationsEnabled;
    }

    public void setEmailNotificationsEnabled(boolean emailNotificationsEnabled) {
        this.emailNotificationsEnabled = emailNotificationsEnabled;
    }

    public boolean isMarketingAgreed() {
        return marketingAgreed;
    }

    public void setMarketingAgreed(boolean marketingAgreed) {
        this.marketingAgreed = marketingAgreed;
    }

    public String getMarketingTermsVersion() {
        return marketingTermsVersion;
    }

    public void setMarketingTermsVersion(String marketingTermsVersion) {
        this.marketingTermsVersion = marketingTermsVersion;
    }
}