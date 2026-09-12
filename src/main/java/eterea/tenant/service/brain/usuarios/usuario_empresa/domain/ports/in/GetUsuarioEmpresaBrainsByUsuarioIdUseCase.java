package eterea.tenant.service.brain.usuarios.usuario_empresa.domain.ports.in;

import eterea.tenant.service.brain.usuarios.usuario_empresa.domain.model.UsuarioEmpresaBrain;
import java.util.List;
import java.util.UUID;

public interface GetUsuarioEmpresaBrainsByUsuarioIdUseCase {

    List<UsuarioEmpresaBrain> getByUsuarioId(UUID usuarioId);
}
