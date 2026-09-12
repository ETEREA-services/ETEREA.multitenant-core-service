package eterea.tenant.service.brain.usuarios.usuario.domain.ports.in;

import eterea.tenant.service.brain.usuarios.usuario.domain.model.UsuarioBrain;
import java.util.List;

public interface GetAllUsuarioBrainsUseCase {
    List<UsuarioBrain> getAll();
}
