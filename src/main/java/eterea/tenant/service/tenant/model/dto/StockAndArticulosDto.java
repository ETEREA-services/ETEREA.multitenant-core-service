package eterea.tenant.service.tenant.model.dto;

import eterea.tenant.service.tenant.hexagonal.articulomovimiento.domain.model.ArticuloMovimiento;
import eterea.tenant.service.tenant.kotlin.model.StockMovimiento;
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
