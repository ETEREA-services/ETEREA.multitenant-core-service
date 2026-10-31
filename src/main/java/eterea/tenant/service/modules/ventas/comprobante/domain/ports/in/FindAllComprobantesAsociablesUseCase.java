package eterea.tenant.service.modules.ventas.comprobante.domain.ports.in;

import eterea.tenant.service.modules.ventas.comprobante.domain.model.Comprobante;
import java.util.List;

public interface FindAllComprobantesAsociablesUseCase {
    List<Comprobante> findAllAsociables();
    List<Comprobante> findAllAsociables(Byte debita);
}
