package eterea.tenant.service.modules.stock.legacy;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public interface ArticuloSaldoFechaRepositoryCustom {

    BigDecimal calculateSaldo(Integer centroStockId, String articuloId, OffsetDateTime tope);

}
