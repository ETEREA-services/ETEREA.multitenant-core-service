package eterea.tenant.service.modules.ventas.comprobante.domain.ports.in;

import java.util.List;

public interface FindAllComprobantesDisponiblesUseCase {
    List<Integer> findAllDisponibles();
}
