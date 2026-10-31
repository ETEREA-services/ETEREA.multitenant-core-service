package eterea.tenant.service.modules.contable.legacy;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public interface CuentaMovimientoRepositoryCustom {

    BigDecimal calculateTotalByNumeroCuentaAndDebitaAndFechaBetween(
            Long numeroCuenta,
            Integer debita,
            OffsetDateTime desde,
            OffsetDateTime hasta);

    BigDecimal calculateTotalByNumeroCuentaAndDebitaAndInflacionAndFechaBetween(
            Long numeroCuenta,
            Integer debita,
            Integer inflacion,
            OffsetDateTime desde,
            OffsetDateTime hasta);

    void deleteAllByFechaAndOrden(OffsetDateTime fechaContable, Integer ordenContable);

}
