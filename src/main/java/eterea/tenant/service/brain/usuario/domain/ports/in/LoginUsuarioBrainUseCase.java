package eterea.tenant.service.brain.usuario.domain.ports.in;

import eterea.tenant.service.brain.usuario.domain.model.UsuarioBrain;
import java.util.Optional;

public interface LoginUsuarioBrainUseCase {
    Optional<UsuarioBrain> login(String login, String password, String ip);
}
