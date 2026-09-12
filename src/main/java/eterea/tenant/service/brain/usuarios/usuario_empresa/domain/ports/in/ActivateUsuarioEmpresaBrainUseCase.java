package eterea.tenant.service.brain.usuarios.usuario_empresa.domain.ports.in;

import eterea.tenant.service.brain.usuarios.usuario_empresa.domain.model.UsuarioEmpresaBrain;
import java.util.Optional;
import java.util.UUID;

public interface ActivateUsuarioEmpresaBrainUseCase {

    Optional<UsuarioEmpresaBrain> activate(UUID usuarioEmpresaId);
}
