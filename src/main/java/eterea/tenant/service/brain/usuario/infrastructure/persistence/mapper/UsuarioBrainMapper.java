package eterea.tenant.service.brain.usuario.infrastructure.persistence.mapper;

import eterea.tenant.service.brain.usuario.domain.model.UsuarioBrain;
import eterea.tenant.service.brain.usuario.infrastructure.persistence.entity.UsuarioBrainEntity;
import org.springframework.stereotype.Component;

@Component
public class UsuarioBrainMapper {

    public UsuarioBrain toDomain(UsuarioBrainEntity entity) {
        if (entity == null) {
            return null;
        }
        return UsuarioBrain.builder()
                .usuarioId(entity.getUsuarioId())
                .login(entity.getLogin())
                .nombre(entity.getNombre())
                .email(entity.getEmail())
                .passwordHash(entity.getPasswordHash())
                .activo(entity.getActivo())
                .lastLoginAt(entity.getLastLoginAt())
                .lastLoginIp(entity.getLastLoginIp())
                .failedLoginAttempts(entity.getFailedLoginAttempts())
                .lockedUntil(entity.getLockedUntil())
                .passwordChangedAt(entity.getPasswordChangedAt())
                .build();
    }

    public UsuarioBrainEntity toEntity(UsuarioBrain domain) {
        if (domain == null) {
            return null;
        }
        return UsuarioBrainEntity.builder()
                .usuarioId(domain.getUsuarioId())
                .login(domain.getLogin())
                .nombre(domain.getNombre())
                .email(domain.getEmail())
                .passwordHash(domain.getPasswordHash())
                .activo(domain.getActivo())
                .lastLoginAt(domain.getLastLoginAt())
                .lastLoginIp(domain.getLastLoginIp())
                .failedLoginAttempts(domain.getFailedLoginAttempts())
                .lockedUntil(domain.getLockedUntil())
                .passwordChangedAt(domain.getPasswordChangedAt())
                .build();
    }
}
