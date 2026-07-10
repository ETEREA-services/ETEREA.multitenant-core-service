package eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.infrastructure.web.mapper;

import eterea.tenant.service.unificado.hexagonal.negocioUnificado.infrastructure.web.mapper.NegocioUnificadoDtoMapper;
import eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.domain.model.UsuarioNegocioUnificado;
import eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.infrastructure.web.dto.UsuarioNegocioUnificadoResponse;
import eterea.tenant.service.unificado.hexagonal.usuarioUnificado.infrastructure.web.mapper.UsuarioUnificadoDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioNegocioUnificadoDtoMapper {

    private final UsuarioUnificadoDtoMapper usuarioUnificadoDtoMapper;
    private final NegocioUnificadoDtoMapper negocioUnificadoDtoMapper;

    public UsuarioNegocioUnificadoResponse toResponse(UsuarioNegocioUnificado domain) {
        if (domain == null) {
            return null;
        }
        return UsuarioNegocioUnificadoResponse.builder()
                .usuarioNegocioId(domain.getUsuarioNegocioId())
                .usuarioId(domain.getUsuarioId())
                .negocioId(domain.getNegocioId())
                .usuario(usuarioUnificadoDtoMapper.toResponse(domain.getUsuario()))
                .negocio(negocioUnificadoDtoMapper.toResponse(domain.getNegocio()))
                .build();
    }

}
