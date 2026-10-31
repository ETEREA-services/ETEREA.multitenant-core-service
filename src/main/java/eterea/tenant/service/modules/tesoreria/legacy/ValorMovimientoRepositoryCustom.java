package eterea.tenant.service.modules.tesoreria.legacy;

import eterea.tenant.service.modules.tesoreria.legacy.ValorMovimiento;
import java.time.OffsetDateTime;
import java.util.List;

public interface ValorMovimientoRepositoryCustom {

    List<ValorMovimiento> findAllByFechaContableBetween(
            OffsetDateTime desde,
            OffsetDateTime hasta,
            boolean cierreCajaOnly,
            boolean ingresosOnly);

    void deleteAllByFechaContableAndOrdenContable(
            OffsetDateTime fechaContable,
            Integer ordenContable);

}
