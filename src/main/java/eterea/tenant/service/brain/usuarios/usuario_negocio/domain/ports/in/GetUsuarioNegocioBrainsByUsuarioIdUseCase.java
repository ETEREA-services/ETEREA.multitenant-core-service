package eterea.tenant.service.brain.usuarios.usuario_negocio.domain.ports.in;

import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.model.UsuarioNegocioConNegocioBrain;
import java.util.List;
import java.util.UUID;

public interface GetUsuarioNegocioBrainsByUsuarioIdUseCase {

    List<UsuarioNegocioConNegocioBrain> getByUsuarioId(UUID usuarioId);
}
