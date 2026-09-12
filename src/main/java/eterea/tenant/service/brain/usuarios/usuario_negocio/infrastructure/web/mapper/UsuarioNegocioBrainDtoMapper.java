package eterea.tenant.service.brain.usuarios.usuario_negocio.infrastructure.web.mapper;

import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.model.NegocioInfo;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.model.UsuarioNegocioBrain;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.model.UsuarioNegocioConNegocioBrain;
import eterea.tenant.service.brain.usuarios.usuario_negocio.infrastructure.web.dto.NegocioResponse;
import eterea.tenant.service.brain.usuarios.usuario_negocio.infrastructure.web.dto.UsuarioNegocioBrainRequest;
import eterea.tenant.service.brain.usuarios.usuario_negocio.infrastructure.web.dto.UsuarioNegocioBrainResponse;
import eterea.tenant.service.brain.usuarios.usuario_negocio.infrastructure.web.dto.UsuarioNegocioConNegocioResponse;
import org.springframework.stereotype.Component;

@Component
public class UsuarioNegocioBrainDtoMapper {

    public UsuarioNegocioBrain toDomain(UsuarioNegocioBrainRequest request) {
        if (request == null) {
            return null;
        }
        return UsuarioNegocioBrain.builder()
                .usuarioId(request.getUsuarioId())
                .negocioId(request.getNegocioId())
                .build();
    }

    public UsuarioNegocioBrainResponse toResponse(UsuarioNegocioBrain domain) {
        if (domain == null) {
            return null;
        }
        UsuarioNegocioBrainResponse.UsuarioNegocioBrainResponseBuilder builder = UsuarioNegocioBrainResponse.builder()
                .usuarioNegocioId(domain.getUsuarioNegocioId())
                .usuarioId(domain.getUsuarioId())
                .negocioId(domain.getNegocioId());
        if (domain.getActivo() != null) {
            builder.activo(domain.getActivo());
        }
        return builder.build();
    }

    public UsuarioNegocioConNegocioResponse toConNegocioResponse(UsuarioNegocioConNegocioBrain domain) {
        if (domain == null) {
            return null;
        }
        UsuarioNegocioConNegocioResponse.UsuarioNegocioConNegocioResponseBuilder builder =
                UsuarioNegocioConNegocioResponse.builder()
                        .usuarioNegocioId(domain.getUsuarioNegocioId())
                        .usuarioId(domain.getUsuarioId())
                        .negocioId(domain.getNegocioId())
                        .negocio(toNegocioResponse(domain.getNegocio()));
        if (domain.getActivo() != null) {
            builder.activo(domain.getActivo());
        }
        return builder.build();
    }

    private NegocioResponse toNegocioResponse(NegocioInfo negocio) {
        if (negocio == null) {
            return null;
        }
        return NegocioResponse.builder()
                .negocioId(negocio.getNegocioId())
                .empresaId(negocio.getEmpresaId())
                .nombre(negocio.getNombre())
                .tenantId(negocio.getTenantId())
                .build();
    }
}
