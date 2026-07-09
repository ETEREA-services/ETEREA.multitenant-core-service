package eterea.tenant.service.tenant.hexagonal.comprobante.domain.ports.in;

import eterea.tenant.service.tenant.hexagonal.comprobante.domain.model.Comprobante;

public interface FindComprobanteByIdUseCase {
    Comprobante findByComprobanteId(Integer comprobanteId);
}
