package eterea.tenant.service.modules.stock.legacy.model.dto;

import eterea.tenant.service.modules.stock.articulomovimiento.domain.model.ArticuloMovimiento;
import eterea.tenant.service.modules.stock.legacy.StockMovimiento;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockAndArticulosDto {

    private StockMovimiento stockMovimiento;
    private List<ArticuloMovimiento> articuloMovimientos;

}
