package com.playtab.bff.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.playtab.bff.common.error.ApiErrorResponse;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthFilter.class);
    private static final String BEARER_PREFIX = "Bearer ";

    private static final Set<String> PUBLIC_PATHS = Set.of(
            "/api/v1/auth/login/email",
            "/api/v1/auth/login/social",
            "/api/v1/auth/refresh",
            "/api/v1/users/signup",
            "/api/v1/auth/email-verifications/send",
            "/api/v1/auth/email-verifications/verify",
            "/api/v1/auth/password-resets/send",
            "/api/v1/auth/password-resets/verify",
            "/api/v1/auth/password-resets/reset",
            "/api/v1/health"
    );

    private final JwtTokenParser jwtTokenParser;
    private final ObjectMapper objectMapper;

    public JwtAuthFilter(JwtTokenParser jwtTokenParser, ObjectMapper objectMapper) {
        this.jwtTokenParser = jwtTokenParser;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String path = request.getRequestURI();

        // Public REST 경로는 인증 없이 통과
        if (isPublicPath(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        // GraphQL, Swagger 등은 토큰이 있으면 검증, 없으면 통과 (resolver에서 처리)
        if (isOptionalAuthPath(path)) {
            tryAuthenticate(request);
            filterChain.doFilter(request, response);
            return;
        }

        // 그 외 인증 필수 경로
        String token = extractToken(request);
        if (token == null) {
            writeUnauthorized(response, request, "Missing Authorization header");
            return;
        }

        try {
            AuthenticatedUser user = jwtTokenParser.parse(token);
            request.setAttribute(AuthenticatedUser.REQUEST_ATTRIBUTE, user);
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            log.warn("JWT validation failed: {}", e.getMessage());
            writeUnauthorized(response, request, "Invalid or expired token");
        }
    }

    private void tryAuthenticate(HttpServletRequest request) {
        String token = extractToken(request);
        if (token == null) {
            return;
        }
        try {
            AuthenticatedUser user = jwtTokenParser.parse(token);
            request.setAttribute(AuthenticatedUser.REQUEST_ATTRIBUTE, user);
        } catch (JwtException e) {
            log.warn("JWT validation failed for optional auth path: {}", e.getMessage());
        }
    }

    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.contains(path);
    }

    private boolean isOptionalAuthPath(String path) {
        return path.equals("/graphql")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.equals("/graphiql")
                || path.startsWith("/graphiql/");
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith(BEARER_PREFIX)) {
            return header.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    private void writeUnauthorized(
            HttpServletResponse response,
            HttpServletRequest request,
            String message
    ) throws IOException {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ApiErrorResponse body = new ApiErrorResponse(
                OffsetDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                "UNAUTHENTICATED",
                message,
                request.getRequestURI()
        );

        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(), body);
    }
}
