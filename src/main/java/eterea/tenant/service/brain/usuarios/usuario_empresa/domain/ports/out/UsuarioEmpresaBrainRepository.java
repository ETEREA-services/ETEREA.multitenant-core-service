package eterea.tenant.service.brain.usuarios.usuario_empresa.domain.ports.out;

import eterea.tenant.service.brain.usuarios.usuario_empresa.domain.model.UsuarioEmpresaBrain;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioEmpresaBrainRepository {

    List<UsuarioEmpresaBrain> findByUsuarioId(UUID usuarioId);

    Optional<UsuarioEmpresaBrain> findById(UUID usuarioEmpresaId);

    UsuarioEmpresaBrain save(UsuarioEmpresaBrain usuarioEmpresa);
}
