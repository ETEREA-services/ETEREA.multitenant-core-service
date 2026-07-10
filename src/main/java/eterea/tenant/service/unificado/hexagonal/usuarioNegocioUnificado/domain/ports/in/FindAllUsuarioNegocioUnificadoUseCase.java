package eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.domain.ports.in;

import eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.domain.model.UsuarioNegocioUnificado;
import java.util.List;

public interface FindAllUsuarioNegocioUnificadoUseCase {
    List<UsuarioNegocioUnificado> findAll();
}
