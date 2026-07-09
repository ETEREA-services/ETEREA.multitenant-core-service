package eterea.tenant.service.tenant.hexagonal.comprobante.domain.ports.in;

import java.util.List;

public interface FindAllComprobantesDisponiblesUseCase {
    List<Integer> findAllDisponibles();
}
