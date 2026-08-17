package eterea.tenant.service.brain.usuario.infrastructure.web.mapper;

import eterea.tenant.service.brain.usuario.domain.model.UsuarioBrain;
import eterea.tenant.service.brain.usuario.infrastructure.web.dto.UsuarioBrainRequest;
import eterea.tenant.service.brain.usuario.infrastructure.web.dto.UsuarioBrainResponse;
import org.springframework.stereotype.Component;

@Component
public class UsuarioBrainDtoMapper {

    public UsuarioBrain toDomain(UsuarioBrainRequest request) {
        if (request == null) {
            return null;
        }
        return UsuarioBrain.builder()
                .login(request.getLogin())
                .nombre(request.getNombre())
                .email(request.getEmail())
                .activo(request.getActivo())
                .build();
    }

    public UsuarioBrainResponse toResponse(UsuarioBrain domain) {
        if (domain == null) {
            return null;
        }
        return UsuarioBrainResponse.builder()
                .usuarioId(domain.getUsuarioId())
                .login(domain.getLogin())
                .nombre(domain.getNombre())
                .email(domain.getEmail())
                .activo(domain.getActivo())
                .lastLoginAt(domain.getLastLoginAt())
                .lastLoginIp(domain.getLastLoginIp())
                .failedLoginAttempts(domain.getFailedLoginAttempts())
                .lockedUntil(domain.getLockedUntil())
                .passwordChangedAt(domain.getPasswordChangedAt())
                .build();
    }
}
