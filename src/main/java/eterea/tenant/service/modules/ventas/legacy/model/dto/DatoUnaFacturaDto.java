package eterea.tenant.service.modules.ventas.legacy.model.dto;

import eterea.tenant.service.modules.stock.articulomovimiento.domain.model.ArticuloMovimiento;
import eterea.tenant.service.modules.ventas.legacy.model.ClienteMovimiento;
import lombok.Data;

@Data
public class DatoUnaFacturaDto {

        private ClienteMovimiento clienteMovimiento;
        private ArticuloMovimiento articuloMovimiento;

}
