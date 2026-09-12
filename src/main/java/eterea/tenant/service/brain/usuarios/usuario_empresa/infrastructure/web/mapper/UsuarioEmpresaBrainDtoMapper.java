package eterea.tenant.service.brain.usuarios.usuario_empresa.infrastructure.web.mapper;

import eterea.tenant.service.brain.usuarios.usuario_empresa.domain.model.UsuarioEmpresaBrain;
import eterea.tenant.service.brain.usuarios.usuario_empresa.infrastructure.web.dto.UsuarioEmpresaBrainRequest;
import eterea.tenant.service.brain.usuarios.usuario_empresa.infrastructure.web.dto.UsuarioEmpresaBrainResponse;
import org.springframework.stereotype.Component;

@Component
public class UsuarioEmpresaBrainDtoMapper {

    public UsuarioEmpresaBrain toDomain(UsuarioEmpresaBrainRequest request) {
        if (request == null) {
            return null;
        }
        return UsuarioEmpresaBrain.builder()
                .usuarioId(request.getUsuarioId())
                .empresaId(request.getEmpresaId())
                .build();
    }

    public UsuarioEmpresaBrainResponse toResponse(UsuarioEmpresaBrain domain) {
        if (domain == null) {
            return null;
        }
        UsuarioEmpresaBrainResponse.UsuarioEmpresaBrainResponseBuilder builder = UsuarioEmpresaBrainResponse.builder()
                .usuarioEmpresaId(domain.getUsuarioEmpresaId())
                .usuarioId(domain.getUsuarioId())
                .empresaId(domain.getEmpresaId());
        if (domain.getActivo() != null) {
            builder.activo(domain.getActivo());
        }
        return builder.build();
    }
}
