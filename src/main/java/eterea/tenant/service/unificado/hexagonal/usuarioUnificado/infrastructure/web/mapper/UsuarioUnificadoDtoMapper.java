package eterea.tenant.service.unificado.hexagonal.usuarioUnificado.infrastructure.web.mapper;

import eterea.tenant.service.unificado.hexagonal.usuarioUnificado.domain.model.UsuarioUnificado;
import eterea.tenant.service.unificado.hexagonal.usuarioUnificado.infrastructure.web.dto.UsuarioUnificadoResponse;
import org.springframework.stereotype.Component;

@Component
public class UsuarioUnificadoDtoMapper {

    public UsuarioUnificadoResponse toResponse(UsuarioUnificado domain) {
        if (domain == null) {
            return null;
        }
        return UsuarioUnificadoResponse.builder()
                .usuarioId(domain.getUsuarioId())
                .login(domain.getLogin())
                .password(domain.getPassword())
                .build();
    }

}
