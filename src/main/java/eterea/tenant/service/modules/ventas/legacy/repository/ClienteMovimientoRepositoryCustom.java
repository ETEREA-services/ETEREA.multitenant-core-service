package eterea.tenant.service.modules.ventas.legacy.repository;

import java.time.OffsetDateTime;

public interface ClienteMovimientoRepositoryCustom {

    void deleteAllByFechaComprobanteAndComprobanteIdAndPuntoVentaAndNumeroComprobante(
            OffsetDateTime fechaComprobante,
            Integer comprobanteId,
            Integer puntoVenta,
            Long numeroComprobante);

}
