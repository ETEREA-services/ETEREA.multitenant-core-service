package eterea.tenant.service.brain.auth.application.service;

import eterea.tenant.service.brain.auth.infrastructure.persistence.entity.RefreshTokenEntity;
import eterea.tenant.service.brain.auth.infrastructure.persistence.repository.JpaRefreshTokenRepository;
import eterea.tenant.service.configuration.SecurityProperties;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.HexFormat;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final Base64.Encoder ENCODER = Base64.getUrlEncoder().withoutPadding();

    private final JpaRefreshTokenRepository repository;
    private final SecurityProperties securityProperties;

    @Transactional
    public String create(String usuarioId, String userAgent, String ipAddress) {
        String rawToken = generateToken();
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);

        RefreshTokenEntity entity = RefreshTokenEntity.builder()
                .refreshTokenId(UUID.randomUUID().toString())
                .usuarioId(usuarioId)
                .tokenHash(hash(rawToken))
                .createdAt(now)
                .expiresAt(now.plus(securityProperties.getRefreshTokenTtl()))
                .userAgent(truncate(userAgent, 255))
                .ipAddress(truncate(ipAddress, 64))
                .build();
        repository.save(entity);

        return rawToken;
    }

    /**
     * Valida el refresh token y extiende su vigencia. No rota el valor en cada
     * request para permitir que Contable y Administrador compartan la misma
     * sesión sin invalidarse mutuamente cuando refrescan casi al mismo tiempo.
     */
    @Transactional
    public Optional<String> touch(String rawToken) {
        if (rawToken == null || rawToken.isBlank()) {
            return Optional.empty();
        }

        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        return repository.findByTokenHash(hash(rawToken))
                .filter(token -> token.getRevokedAt() == null)
                .filter(token -> token.getExpiresAt().isAfter(now))
                .map(token -> {
                    token.setLastUsedAt(now);
                    token.setExpiresAt(now.plus(securityProperties.getRefreshTokenTtl()));
                    repository.save(token);
                    return token.getUsuarioId();
                });
    }

    @Transactional
    public void revoke(String rawToken) {
        if (rawToken == null || rawToken.isBlank()) {
            return;
        }

        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        repository.findByTokenHash(hash(rawToken))
                .filter(token -> token.getRevokedAt() == null)
                .ifPresent(token -> {
                    token.setRevokedAt(now);
                    repository.save(token);
                });
    }

    @Transactional
    public void revokeAllForUsuario(String usuarioId) {
        if (usuarioId == null || usuarioId.isBlank()) {
            return;
        }

        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        repository.findAllByUsuarioIdAndRevokedAtIsNull(usuarioId)
                .forEach(token -> {
                    token.setRevokedAt(now);
                    repository.save(token);
                });
    }

    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void limpiarExpirados() {
        repository.deleteByExpiresAtBefore(OffsetDateTime.now(ZoneOffset.UTC));
    }

    private String generateToken() {
        byte[] bytes = new byte[32];
        RANDOM.nextBytes(bytes);
        return ENCODER.encodeToString(bytes);
    }

    private String hash(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(digest.digest(value.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 no disponible", exception);
        }
    }

    private String truncate(String value, int maxLength) {
        if (value == null) {
            return null;
        }
        return value.length() <= maxLength ? value : value.substring(0, maxLength);
    }

}
