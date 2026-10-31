package eterea.tenant.service.modules.stock.articulo.domain.ports.in;

import eterea.tenant.service.modules.stock.articulo.infrastructure.web.dto.TotalArticuloResponse;
import java.math.BigDecimal;

public interface CalculateTotalesByArticuloUseCase {

    TotalArticuloResponse calculateTotales(String articuloId, BigDecimal cantidad, BigDecimal precioUnitarioConIva);

}
