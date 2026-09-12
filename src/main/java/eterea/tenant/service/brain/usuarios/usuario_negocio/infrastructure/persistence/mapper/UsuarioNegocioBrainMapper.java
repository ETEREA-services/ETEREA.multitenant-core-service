package eterea.tenant.service.brain.usuarios.usuario_negocio.infrastructure.persistence.mapper;

import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.model.UsuarioNegocioBrain;
import eterea.tenant.service.brain.usuarios.usuario_negocio.infrastructure.persistence.entity.UsuarioNegocioBrainEntity;
import org.springframework.stereotype.Component;

@Component
public class UsuarioNegocioBrainMapper {

    public UsuarioNegocioBrain toDomain(UsuarioNegocioBrainEntity entity) {
        if (entity == null) {
            return null;
        }
        UsuarioNegocioBrain.UsuarioNegocioBrainBuilder builder = UsuarioNegocioBrain.builder()
                .usuarioNegocioId(entity.getUsuarioNegocioId())
                .usuarioId(entity.getUsuarioId())
                .negocioId(entity.getNegocioId());
        if (entity.getActivo() != null) {
            builder.activo(entity.getActivo());
        }
        return builder.build();
    }

    public UsuarioNegocioBrainEntity toEntity(UsuarioNegocioBrain domain) {
        if (domain == null) {
            return null;
        }
        UsuarioNegocioBrainEntity.UsuarioNegocioBrainEntityBuilder builder = UsuarioNegocioBrainEntity.builder()
                .usuarioNegocioId(domain.getUsuarioNegocioId())
                .usuarioId(domain.getUsuarioId())
                .negocioId(domain.getNegocioId());
        if (domain.getActivo() != null) {
            builder.activo(domain.getActivo());
        }
        return builder.build();
    }
}
