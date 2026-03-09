package com.playtab.bff.auth.rest;

import com.playtab.bff.auth.dto.request.*;
import com.playtab.bff.auth.dto.response.AuthTokensResponseDto;
import com.playtab.bff.auth.dto.response.SendEmailVerificationCodeResponseDto;
import com.playtab.bff.auth.dto.response.SignUpResponseDto;
import com.playtab.bff.auth.dto.response.SuccessResponseDto;
import com.playtab.bff.auth.dto.response.VerifyEmailCodeResponseDto;
import com.playtab.bff.auth.service.AuthFacade;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AuthRestController {

    private final AuthFacade authFacade;

    public AuthRestController(AuthFacade authFacade) {
        this.authFacade = authFacade;
    }

    @PostMapping("/auth/login/email")
    public AuthTokensResponseDto loginWithEmail(
            @RequestBody LoginWithEmailRequestDto request,
            HttpServletRequest httpServletRequest
    ) {
        return authFacade.loginWithEmail(request, httpServletRequest);
    }

    @PostMapping("/auth/refresh")
    public AuthTokensResponseDto refreshTokens(
            @RequestBody RefreshTokensRequestDto request,
            HttpServletRequest httpServletRequest
    ) {
        return authFacade.refreshTokens(request, httpServletRequest);
    }

    @PostMapping("/auth/logout")
    public SuccessResponseDto logout(@RequestBody LogoutRequestDto request) {
        return authFacade.logout(request);
    }

    @PostMapping("/users/signup")
    public SignUpResponseDto signUpWithEmail(@RequestBody SignUpWithEmailRequestDto request) {
        return authFacade.signUpWithEmail(request);
    }

    @PostMapping("/auth/email-verifications/send")
    public SendEmailVerificationCodeResponseDto sendEmailVerificationCode(
            @RequestBody SendEmailVerificationCodeRequestDto request
    ) {
        return authFacade.sendEmailVerificationCode(request);
    }

    @PostMapping("/auth/email-verifications/verify")
    public VerifyEmailCodeResponseDto verifyEmailCode(
            @RequestBody VerifyEmailCodeRequestDto request
    ) {
        return authFacade.verifyEmailCode(request);
    }

    @PostMapping("/auth/login/social")
    public AuthTokensResponseDto loginWithSocial(
            @RequestBody LoginWithSocialRequestDto request,
            HttpServletRequest httpServletRequest
    ) {
        return authFacade.loginWithSocial(request, httpServletRequest);
    }
}