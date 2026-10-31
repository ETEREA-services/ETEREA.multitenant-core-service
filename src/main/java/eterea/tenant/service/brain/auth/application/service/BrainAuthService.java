package eterea.tenant.service.brain.auth.application.service;

import eterea.tenant.service.modules.platform.legacy.Usuario;
import eterea.tenant.service.brain.auth.application.exception.AdminRequiredException;
import eterea.tenant.service.brain.usuarios.usuario.application.exception.TemporaryPasswordExpiredException;
import eterea.tenant.service.brain.usuarios.usuario.application.exception.UsuarioBrainException;
import eterea.tenant.service.brain.usuarios.usuario.application.service.UsuarioBrainService;
import eterea.tenant.service.brain.usuarios.usuario.domain.model.UsuarioBrain;
import eterea.tenant.service.configuration.SecurityProperties;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrainAuthService {

    private final UsuarioBrainService usuarioBrainService;
    private final RefreshTokenService refreshTokenService;
    private final JwtEncoder jwtEncoder;
    private final SecurityProperties securityProperties;

    public AuthTokens login(
            String login,
            String password,
            String app,
            String ipAddress,
            String userAgent) {
        UsuarioBrain usuario = usuarioBrainService.login(login, password, ipAddress);

        if ("administrador".equalsIgnoreCase(app) && !Boolean.TRUE.equals(usuario.getAdministrador())) {
            throw new AdminRequiredException();
        }

        String accessToken = crearAccessToken(usuario);
        String refreshToken = refreshTokenService.create(
                usuario.getUsuarioId().toString(), userAgent, ipAddress);

        return new AuthTokens(accessToken, securityProperties.getAccessTokenTtl().toSeconds(),
                refreshToken, usuario);
    }

    public AuthTokens refresh(String rawRefreshToken, String ipAddress, String userAgent) {
        String usuarioId = refreshTokenService.touch(rawRefreshToken)
                .orElseThrow(() -> new UsuarioBrainException("Refresh token inválido o expirado"));

        UsuarioBrain usuario;
        try {
            usuario = usuarioBrainService.findById(UUID.fromString(usuarioId));
        } catch (RuntimeException exception) {
            refreshTokenService.revoke(rawRefreshToken);
            throw new UsuarioBrainException("Usuario no encontrado");
        }

        if (!Boolean.TRUE.equals(usuario.getActivo())) {
            refreshTokenService.revoke(rawRefreshToken);
            throw new UsuarioBrainException("Usuario inactivo");
        }

        if (Boolean.TRUE.equals(usuario.getMustChangePassword())
                && usuario.getTemporaryPasswordExpiresAt() != null
                && !usuario.getTemporaryPasswordExpiresAt().isAfter(java.time.OffsetDateTime.now())) {
            refreshTokenService.revoke(rawRefreshToken);
            throw new TemporaryPasswordExpiredException();
        }

        return new AuthTokens(crearAccessToken(usuario),
                securityProperties.getAccessTokenTtl().toSeconds(),
                rawRefreshToken, usuario);
    }

    public void logout(String rawRefreshToken) {
        refreshTokenService.revoke(rawRefreshToken);
    }

    public UsuarioBrain me(UUID usuarioId) {
        return usuarioBrainService.findById(usuarioId);
    }

    private String crearAccessToken(UsuarioBrain usuario) {
        Instant now = Instant.now();
        boolean administrador = Boolean.TRUE.equals(usuario.getAdministrador());

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(securityProperties.getIssuer())
                .subject(usuario.getUsuarioId().toString())
                .issuedAt(now)
                .expiresAt(now.plus(securityProperties.getAccessTokenTtl()))
                .id(UUID.randomUUID().toString())
                .claim("login", usuario.getLogin())
                .claim("nombre", usuario.getNombre())
                .claim("email", usuario.getEmail())
                .claim("administrador", administrador)
                .claim("authorities", administrador ? List.of("ADMIN") : List.of())
                .claim("pwd_change_required", Boolean.TRUE.equals(usuario.getMustChangePassword()))
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        Jwt jwt = jwtEncoder.encode(JwtEncoderParameters.from(header, claims));
        return jwt.getTokenValue();
    }

    public record AuthTokens(
            String accessToken,
            long expiresIn,
            String refreshToken,
            UsuarioBrain usuario) {
    }

}
