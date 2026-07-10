package eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.infrastructure.persistence.mapper;

import eterea.tenant.service.unificado.hexagonal.negocioUnificado.infrastructure.persistence.mapper.NegocioUnificadoMapper;
import eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.domain.model.UsuarioNegocioUnificado;
import eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.infrastructure.persistence.entity.UsuarioNegocioUnificadoEntity;
import eterea.tenant.service.unificado.hexagonal.usuarioUnificado.infrastructure.persistence.mapper.UsuarioUnificadoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioNegocioUnificadoMapper {

    private final UsuarioUnificadoMapper usuarioUnificadoMapper;
    private final NegocioUnificadoMapper negocioUnificadoMapper;

    public UsuarioNegocioUnificado toDomain(UsuarioNegocioUnificadoEntity entity) {
        if (entity == null) {
            return null;
        }
        return UsuarioNegocioUnificado.builder()
                .usuarioNegocioId(entity.getUsuarioNegocioId())
                .usuarioId(entity.getUsuarioId())
                .negocioId(entity.getNegocioId())
                .usuario(usuarioUnificadoMapper.toDomain(entity.getUsuario()))
                .negocio(negocioUnificadoMapper.toDomain(entity.getNegocio()))
                .build();
    }

    public UsuarioNegocioUnificadoEntity toEntity(UsuarioNegocioUnificado domain) {
        if (domain == null) {
            return null;
        }
        return UsuarioNegocioUnificadoEntity.builder()
                .usuarioNegocioId(domain.getUsuarioNegocioId())
                .usuarioId(domain.getUsuarioId())
                .negocioId(domain.getNegocioId())
                .usuario(usuarioUnificadoMapper.toEntity(domain.getUsuario()))
                .negocio(negocioUnificadoMapper.toEntity(domain.getNegocio()))
                .build();
    }

}
