package eterea.tenant.service.unificado.hexagonal.usuarioUnificado.domain.ports.in;

import eterea.tenant.service.unificado.hexagonal.usuarioUnificado.domain.model.UsuarioUnificado;

public interface FindUsuarioUnificadoByLoginUseCase {
    UsuarioUnificado findByLogin(String login);
}
