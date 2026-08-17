package eterea.tenant.service.brain.usuario.domain.ports.in;

import eterea.tenant.service.brain.usuario.domain.model.UsuarioBrain;
import java.util.List;

public interface GetAllUsuarioBrainsUseCase {
    List<UsuarioBrain> getAll();
}
