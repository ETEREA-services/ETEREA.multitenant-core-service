package eterea.tenant.service.tenant.hexagonal.comprobante.domain.ports.in;

import eterea.tenant.service.tenant.hexagonal.comprobante.domain.model.Comprobante;
import java.util.List;

public interface FindAllComprobantesAsociablesUseCase {
    List<Comprobante> findAllAsociables();
    List<Comprobante> findAllAsociables(Byte debita);
}
