package eterea.tenant.service.brain.usuarios.usuario_negocio.domain.ports.out;

import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.model.UsuarioNegocioBrain;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioNegocioBrainRepository {

    List<UsuarioNegocioBrain> findByUsuarioId(UUID usuarioId);

    Optional<UsuarioNegocioBrain> findById(UUID usuarioNegocioId);

    Optional<UsuarioNegocioBrain> findByUsuarioIdAndNegocioId(UUID usuarioId, Integer negocioId);

    UsuarioNegocioBrain save(UsuarioNegocioBrain usuarioNegocio);
}
