package eterea.tenant.service.unificado.hexagonal.usuarioUnificado.infrastructure.persistence.mapper;

import eterea.tenant.service.unificado.hexagonal.usuarioUnificado.domain.model.UsuarioUnificado;
import eterea.tenant.service.unificado.hexagonal.usuarioUnificado.infrastructure.persistence.entity.UsuarioUnificadoEntity;
import org.springframework.stereotype.Component;

@Component
public class UsuarioUnificadoMapper {

    public UsuarioUnificado toDomain(UsuarioUnificadoEntity entity) {
        if (entity == null) {
            return null;
        }
        return UsuarioUnificado.builder()
                .usuarioId(entity.getUsuarioId())
                .login(entity.getLogin())
                .password(entity.getPassword())
                .build();
    }

    public UsuarioUnificadoEntity toEntity(UsuarioUnificado domain) {
        if (domain == null) {
            return null;
        }
        return UsuarioUnificadoEntity.builder()
                .usuarioId(domain.getUsuarioId())
                .login(domain.getLogin())
                .password(domain.getPassword())
                .build();
    }

}
