package eterea.tenant.service.modules.ventas.comprobante.domain.ports.in;

import eterea.tenant.service.modules.ventas.comprobante.domain.model.Comprobante;

public interface FindComprobanteByIdUseCase {
    Comprobante findByComprobanteId(Integer comprobanteId);
}
