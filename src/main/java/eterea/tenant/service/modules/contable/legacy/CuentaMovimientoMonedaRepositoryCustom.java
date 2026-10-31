package eterea.tenant.service.modules.contable.legacy;

import java.time.OffsetDateTime;

public interface CuentaMovimientoMonedaRepositoryCustom {

    void insertOrUpdateMovimientosCotizados(
            Integer monedaIdOrigen,
            Integer monedaIdDestino,
            OffsetDateTime fechaDesde,
            OffsetDateTime fechaHasta);

    int deleteOrphanedRecords(
            Integer monedaId,
            OffsetDateTime fechaDesde,
            OffsetDateTime fechaHasta);

}
