package eterea.tenant.service.tenant.hexagonal.articulo.domain.ports.in;

import eterea.tenant.service.tenant.hexagonal.articulo.infrastructure.web.dto.TotalArticuloResponse;
import java.math.BigDecimal;

public interface CalculateTotalesByArticuloUseCase {

    TotalArticuloResponse calculateTotales(String articuloId, BigDecimal cantidad, BigDecimal precioUnitarioConIva);

}
