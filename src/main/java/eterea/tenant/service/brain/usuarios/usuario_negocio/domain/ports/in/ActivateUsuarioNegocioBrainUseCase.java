package eterea.tenant.service.brain.usuarios.usuario_negocio.domain.ports.in;

import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.model.UsuarioNegocioBrain;
import java.util.Optional;
import java.util.UUID;

public interface ActivateUsuarioNegocioBrainUseCase {

    Optional<UsuarioNegocioBrain> activate(UUID usuarioNegocioId);
}
