package eterea.tenant.service.brain.auth.infrastructure.web.controller;

import eterea.tenant.service.brain.auth.application.exception.AdminRequiredException;
import eterea.tenant.service.brain.auth.application.service.BrainAuthService;
import eterea.tenant.service.brain.auth.infrastructure.web.dto.AuthTokenResponse;
import eterea.tenant.service.brain.auth.infrastructure.web.dto.BrainAuthLoginRequest;
import eterea.tenant.service.brain.usuarios.usuario.application.exception.TemporaryPasswordExpiredException;
import eterea.tenant.service.brain.usuarios.usuario.application.exception.UsuarioBrainException;
import eterea.tenant.service.brain.usuarios.usuario.domain.model.UsuarioBrain;
import eterea.tenant.service.brain.usuarios.usuario.infrastructure.web.dto.UsuarioBrainResponse;
import eterea.tenant.service.brain.usuarios.usuario.infrastructure.web.mapper.UsuarioBrainDtoMapper;
import eterea.tenant.service.configuration.SecurityProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.time.Duration;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/brain/auth")
@RequiredArgsConstructor
public class BrainAuthController {

    public static final String REFRESH_COOKIE = "eterea_refresh";
    private static final String COOKIE_PATH = "/api/brain/auth";

    private final BrainAuthService brainAuthService;
    private final UsuarioBrainDtoMapper usuarioBrainDtoMapper;
    private final SecurityProperties securityProperties;

    @PostMapping("/login")
    public ResponseEntity<AuthTokenResponse> login(
            @Valid @RequestBody BrainAuthLoginRequest request,
            HttpServletRequest httpRequest) {
        try {
            BrainAuthService.AuthTokens tokens = brainAuthService.login(
                    request.getLogin(),
                    request.getPassword(),
                    request.getApp(),
                    resolveClientIp(httpRequest),
                    httpRequest.getHeader(HttpHeaders.USER_AGENT));

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, refreshCookie(tokens.refreshToken()).toString())
                    .body(toResponse(tokens));
        } catch (AdminRequiredException exception) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, exception.getMessage(), exception);
        } catch (TemporaryPasswordExpiredException exception) {
            throw new ResponseStatusException(
                    HttpStatus.PRECONDITION_REQUIRED, exception.getMessage(), exception);
        } catch (UsuarioBrainException exception) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Credenciales inválidas", exception);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthTokenResponse> refresh(
            @CookieValue(name = REFRESH_COOKIE, required = false) String refreshToken,
            HttpServletRequest httpRequest) {
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token ausente");
        }

        try {
            BrainAuthService.AuthTokens tokens = brainAuthService.refresh(
                    refreshToken,
                    resolveClientIp(httpRequest),
                    httpRequest.getHeader(HttpHeaders.USER_AGENT));

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, refreshCookie(tokens.refreshToken()).toString())
                    .body(toResponse(tokens));
        } catch (UsuarioBrainException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .header(HttpHeaders.SET_COOKIE, clearRefreshCookie().toString())
                    .build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @CookieValue(name = REFRESH_COOKIE, required = false) String refreshToken) {
        brainAuthService.logout(refreshToken);
        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, clearRefreshCookie().toString())
                .build();
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioBrainResponse> me(@AuthenticationPrincipal Jwt jwt) {
        try {
            UsuarioBrain usuario = brainAuthService.me(UUID.fromString(jwt.getSubject()));
            return ResponseEntity.ok(usuarioBrainDtoMapper.toResponse(usuario));
        } catch (RuntimeException exception) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Sesión inválida", exception);
        }
    }

    private AuthTokenResponse toResponse(BrainAuthService.AuthTokens tokens) {
        return AuthTokenResponse.builder()
                .accessToken(tokens.accessToken())
                .tokenType("Bearer")
                .expiresIn(tokens.expiresIn())
                .usuario(usuarioBrainDtoMapper.toResponse(tokens.usuario()))
                .build();
    }

    private ResponseCookie refreshCookie(String value) {
        return ResponseCookie.from(REFRESH_COOKIE, value)
                .httpOnly(true)
                .secure(securityProperties.isCookieSecure())
                .sameSite("Strict")
                .path(COOKIE_PATH)
                .maxAge(securityProperties.getRefreshTokenTtl())
                .build();
    }

    private ResponseCookie clearRefreshCookie() {
        return ResponseCookie.from(REFRESH_COOKIE, "")
                .httpOnly(true)
                .secure(securityProperties.isCookieSecure())
                .sameSite("Strict")
                .path(COOKIE_PATH)
                .maxAge(Duration.ZERO)
                .build();
    }

    private String resolveClientIp(HttpServletRequest request) {
        String forwardedFor = request.getHeader("X-Forwarded-For");
        if (forwardedFor != null && !forwardedFor.isBlank()) {
            return forwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

}
