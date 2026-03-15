package com.playtab.bff.common.util;

import com.playtab.userservice.proto.v1.ClientContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class ClientContextExtractor {

    private static final String DEVICE_FINGERPRINT_HEADER = "X-Device-Fingerprint";
    private static final String X_FORWARDED_FOR = "X-Forwarded-For";
    private static final String X_REAL_IP = "X-Real-IP";

    public ClientContext extract(HttpServletRequest request, String deviceFingerprint) {
        return ClientContext.newBuilder()
                .setDeviceFingerprint(resolveDeviceFingerprint(request, deviceFingerprint))
                .setUserAgent(nullToEmpty(request.getHeader("User-Agent")))
                .setIpAddress(resolveClientIp(request))
                .build();
    }

    private String resolveDeviceFingerprint(HttpServletRequest request, String deviceFingerprint) {
        if (hasText(deviceFingerprint)) {
            return deviceFingerprint;
        }

        String headerValue = request.getHeader(DEVICE_FINGERPRINT_HEADER);
        if (hasText(headerValue)) {
            return headerValue;
        }

        return "";
    }

    private String resolveClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader(X_FORWARDED_FOR);
        if (hasText(xForwardedFor)) {
            String firstIp = xForwardedFor.split(",")[0].trim();
            if (hasText(firstIp)) {
                return firstIp;
            }
        }

        String xRealIp = request.getHeader(X_REAL_IP);
        if (hasText(xRealIp)) {
            return xRealIp.trim();
        }

        return nullToEmpty(request.getRemoteAddr());
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    private String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
}