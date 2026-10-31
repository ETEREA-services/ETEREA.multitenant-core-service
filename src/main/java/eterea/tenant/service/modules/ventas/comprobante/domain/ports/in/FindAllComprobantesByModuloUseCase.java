package eterea.tenant.service.modules.ventas.comprobante.domain.ports.in;

import eterea.tenant.service.modules.ventas.comprobante.domain.model.Comprobante;
import java.util.List;

public interface FindAllComprobantesByModuloUseCase {
    List<Comprobante> findAllByModulo(Integer modulo, Byte debita, Integer comprobanteId);
}
