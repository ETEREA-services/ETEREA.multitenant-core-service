package eterea.tenant.service.unificado.hexagonal.usuarioUnificado.domain.ports.out;

import eterea.tenant.service.unificado.hexagonal.usuarioUnificado.domain.model.UsuarioUnificado;
import java.util.List;
import java.util.Optional;

public interface UsuarioUnificadoRepository {

    List<UsuarioUnificado> findAll();

    Optional<UsuarioUnificado> findByUsuarioId(Integer usuarioId);

    Optional<UsuarioUnificado> findByLogin(String login);

}
