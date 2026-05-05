package com.playtab.bff.auth.rest;

import com.playtab.bff.auth.dto.request.*;
import com.playtab.bff.auth.dto.response.AuthTokensResponseDto;
import com.playtab.bff.auth.dto.response.SendEmailVerificationCodeResponseDto;
import com.playtab.bff.auth.dto.response.SendPasswordResetCodeResponseDto;
import com.playtab.bff.auth.dto.response.SignUpResponseDto;
import com.playtab.bff.auth.dto.response.SuccessResponseDto;
import com.playtab.bff.auth.dto.response.VerifyEmailCodeResponseDto;
import com.playtab.bff.auth.service.AuthFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "인증 관련 API")
@RestController
@RequestMapping("/api/v1")
public class AuthRestController {

    private final AuthFacade authFacade;

    public AuthRestController(AuthFacade authFacade) {
        this.authFacade = authFacade;
    }

    @Operation(summary = "이메일 로그인")
    @PostMapping("/auth/login/email")
    public AuthTokensResponseDto loginWithEmail(
            @RequestBody LoginWithEmailRequestDto request,
            HttpServletRequest httpServletRequest
    ) {
        return authFacade.loginWithEmail(request, httpServletRequest);
    }

    @Operation(summary = "액세스 토큰 갱신 (리프레시 토큰 사용)")
    @PostMapping("/auth/refresh")
    public AuthTokensResponseDto refreshTokens(
            @RequestBody RefreshTokensRequestDto request,
            HttpServletRequest httpServletRequest
    ) {
        return authFacade.refreshTokens(request, httpServletRequest);
    }

    @Operation(summary = "로그아웃")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/auth/logout")
    public SuccessResponseDto logout(@RequestBody LogoutRequestDto request) {
        return authFacade.logout(request);
    }

    @Operation(summary = "이메일 회원가입")
    @PostMapping("/users/signup")
    public SignUpResponseDto signUpWithEmail(@RequestBody SignUpWithEmailRequestDto request) {
        return authFacade.signUpWithEmail(request);
    }

    @Operation(summary = "이메일 인증 코드 발송")
    @PostMapping("/auth/email-verifications/send")
    public SendEmailVerificationCodeResponseDto sendEmailVerificationCode(
            @RequestBody SendEmailVerificationCodeRequestDto request
    ) {
        return authFacade.sendEmailVerificationCode(request);
    }

    @Operation(summary = "이메일 인증 코드 검증")
    @PostMapping("/auth/email-verifications/verify")
    public VerifyEmailCodeResponseDto verifyEmailCode(
            @RequestBody VerifyEmailCodeRequestDto request
    ) {
        return authFacade.verifyEmailCode(request);
    }

    @Operation(summary = "비밀번호 재설정 코드 발송")
    @PostMapping("/auth/password-resets/send")
    public SendPasswordResetCodeResponseDto sendPasswordResetCode(
            @RequestBody SendPasswordResetCodeRequestDto request
    ) {
        return authFacade.sendPasswordResetCode(request);
    }

    @Operation(summary = "비밀번호 재설정 코드 검증")
    @PostMapping("/auth/password-resets/verify")
    public SuccessResponseDto verifyPasswordResetCode(
            @RequestBody VerifyPasswordResetCodeRequestDto request
    ) {
        return authFacade.verifyPasswordResetCode(request);
    }

    @Operation(summary = "비밀번호 재설정")
    @PostMapping("/auth/password-resets/reset")
    public SuccessResponseDto resetPassword(@RequestBody ResetPasswordRequestDto request) {
        return authFacade.resetPassword(request);
    }

    @Operation(summary = "소셜 로그인 (OAuth 제공자 연동) - 사용 X")
    @PostMapping("/auth/login/social")
    public AuthTokensResponseDto loginWithSocial(
            @RequestBody LoginWithSocialRequestDto request,
            HttpServletRequest httpServletRequest
    ) {
        return authFacade.loginWithSocial(request, httpServletRequest);
    }
}