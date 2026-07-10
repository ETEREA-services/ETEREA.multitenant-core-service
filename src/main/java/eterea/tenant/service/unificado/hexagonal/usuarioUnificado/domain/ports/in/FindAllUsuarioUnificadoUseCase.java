package eterea.tenant.service.unificado.hexagonal.usuarioUnificado.domain.ports.in;

import eterea.tenant.service.unificado.hexagonal.usuarioUnificado.domain.model.UsuarioUnificado;
import java.util.List;

public interface FindAllUsuarioUnificadoUseCase {
    List<UsuarioUnificado> findAll();
}
