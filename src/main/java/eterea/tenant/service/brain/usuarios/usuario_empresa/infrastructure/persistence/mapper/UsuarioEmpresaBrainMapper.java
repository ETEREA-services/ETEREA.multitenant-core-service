package eterea.tenant.service.brain.usuarios.usuario_empresa.infrastructure.persistence.mapper;

import eterea.tenant.service.brain.usuarios.usuario_empresa.domain.model.UsuarioEmpresaBrain;
import eterea.tenant.service.brain.usuarios.usuario_empresa.infrastructure.persistence.entity.UsuarioEmpresaBrainEntity;
import org.springframework.stereotype.Component;

@Component
public class UsuarioEmpresaBrainMapper {

    public UsuarioEmpresaBrain toDomain(UsuarioEmpresaBrainEntity entity) {
        if (entity == null) {
            return null;
        }
        UsuarioEmpresaBrain.UsuarioEmpresaBrainBuilder builder = UsuarioEmpresaBrain.builder()
                .usuarioEmpresaId(entity.getUsuarioEmpresaId())
                .usuarioId(entity.getUsuarioId())
                .empresaId(entity.getEmpresaId());
        if (entity.getActivo() != null) {
            builder.activo(entity.getActivo());
        }
        return builder.build();
    }

    public UsuarioEmpresaBrainEntity toEntity(UsuarioEmpresaBrain domain) {
        if (domain == null) {
            return null;
        }
        UsuarioEmpresaBrainEntity.UsuarioEmpresaBrainEntityBuilder builder = UsuarioEmpresaBrainEntity.builder()
                .usuarioEmpresaId(domain.getUsuarioEmpresaId())
                .usuarioId(domain.getUsuarioId())
                .empresaId(domain.getEmpresaId());
        if (domain.getActivo() != null) {
            builder.activo(domain.getActivo());
        }
        return builder.build();
    }
}
