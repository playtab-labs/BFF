package com.playtab.bff.auth.dto.request;

import java.util.List;

public class SignUpWithEmailRequestDto {

    private String email;
    private String password;
    private String name;
    private GenderDto gender;
    private String phoneNumber;
    private String birthDate;
    private String nationality;
    private String sessionId;
    private List<ConsentRequestDto> consents;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GenderDto getGender() {
        return gender;
    }

    public void setGender(GenderDto gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public List<ConsentRequestDto> getConsents() {
        return consents;
    }

    public void setConsents(List<ConsentRequestDto> consents) {
        this.consents = consents;
    }
}