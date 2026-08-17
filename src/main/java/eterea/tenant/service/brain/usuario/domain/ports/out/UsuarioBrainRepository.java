package eterea.tenant.service.brain.usuario.domain.ports.out;

import eterea.tenant.service.brain.usuario.domain.model.UsuarioBrain;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioBrainRepository {
    List<UsuarioBrain> findAll();
    Optional<UsuarioBrain> findById(UUID usuarioId);
    Optional<UsuarioBrain> findByLogin(String login);
    UsuarioBrain save(UsuarioBrain usuario);
}
