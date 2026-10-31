/**
 * 
 */
package eterea.tenant.service.modules.ventas.legacy.model.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import eterea.tenant.service.modules.stock.articulomovimiento.domain.model.ArticuloMovimiento;
import eterea.tenant.service.modules.ventas.comprobante.domain.model.Comprobante;
import eterea.tenant.service.modules.stock.legacy.ArticuloMovimientoTemporal;
import eterea.tenant.service.modules.platform.legacy.Cliente;
import eterea.tenant.service.modules.ventas.legacy.ClienteMovimientoPrevio;
import eterea.tenant.service.modules.stock.legacy.StockMovimiento;
import eterea.tenant.service.modules.ventas.legacy.model.ClienteMovimiento;
import eterea.tenant.service.tool.Jsonifier;
import eterea.tenant.service.tool.Jsonifyable;
import lombok.*;

/**
 * @author daniel
 *
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImpresionFiscalDto implements Serializable, Jsonifyable {

	@Serial
    private static final long serialVersionUID = 8474478112848630563L;

	private Long numeroFactura;
	private Cliente cliente;
	private Comprobante comprobante;
	private List<ArticuloMovimientoTemporal> articuloMovimientoTemporals;
	private ClienteMovimiento comprobanteOrigen;
	private ClienteMovimientoPrevio clienteMovimientoPrevio;
	private StockMovimiento stockMovimiento;
	private List<ArticuloMovimiento> articuloMovimientos;

}
