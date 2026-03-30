package com.playtab.bff.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenParser {

    private final SecretKey secretKey;
    private final String issuer;

    public JwtTokenParser(JwtProperties jwtProperties) {
        byte[] keyBytes = jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.issuer = jwtProperties.getIssuer();
    }

    public AuthenticatedUser parse(String token) throws JwtException {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .requireIssuer(issuer)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String identityId = claims.getSubject();
        String role = claims.get("role", String.class);

        return new AuthenticatedUser(identityId, role);
    }
}
