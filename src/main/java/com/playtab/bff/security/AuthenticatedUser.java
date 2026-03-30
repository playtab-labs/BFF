package com.playtab.bff.security;

public record AuthenticatedUser(String identityId, String role) {

    public static final String REQUEST_ATTRIBUTE = "authenticatedUser";
}
