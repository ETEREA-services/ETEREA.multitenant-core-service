package eterea.tenant.service.configuration;

import eterea.tenant.service.brain.negocio.domain.ports.out.NegocioBrainRepository;
import eterea.tenant.service.brain.usuarios.usuario_empresa.domain.ports.out.UsuarioEmpresaBrainRepository;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.ports.out.UsuarioNegocioBrainRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/** Verifica que un usuario tenga asignada la empresa y el negocio de un tenant. */
@Service
@RequiredArgsConstructor
public class TenantAccessService {

    private final NegocioBrainRepository negocioBrainRepository;
    private final UsuarioEmpresaBrainRepository usuarioEmpresaBrainRepository;
    private final UsuarioNegocioBrainRepository usuarioNegocioBrainRepository;

    public boolean puedeAcceder(UUID usuarioId, String tenantId) {
        return negocioBrainRepository.findByTenantId(tenantId)
                .map(negocio -> usuarioEmpresaBrainRepository
                        .findByUsuarioIdAndEmpresaId(usuarioId, negocio.getEmpresaId())
                        .filter(asignacion -> Boolean.TRUE.equals(asignacion.getActivo()))
                        .isPresent()
                        && usuarioNegocioBrainRepository
                        .findByUsuarioIdAndNegocioId(usuarioId, negocio.getNegocioId())
                        .filter(asignacion -> Boolean.TRUE.equals(asignacion.getActivo()))
                        .isPresent())
                .orElse(false);
    }

}
