package eterea.tenant.service.unificado.hexagonal.negocioUnificado.domain.ports.in;

import eterea.tenant.service.unificado.hexagonal.negocioUnificado.domain.model.NegocioUnificado;
import java.util.List;

public interface FindAllNegocioUnificadoUseCase {
    List<NegocioUnificado> findAll();
}
