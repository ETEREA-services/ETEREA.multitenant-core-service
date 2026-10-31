package eterea.tenant.service.modules.ventas.legacy.model.dto;

import eterea.tenant.service.modules.tesoreria.legacy.ValorMovimiento;
import eterea.tenant.service.modules.contable.legacy.CuentaMovimiento;
import eterea.tenant.service.tool.Jsonifier;
import eterea.tenant.service.tool.Jsonifyable;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferenciaWrapperDto implements Jsonifyable {

    private TransferenciaDto transferencia;
    private List<ValorMovimiento> valorMovimientos;
    private List<CuentaMovimiento> cuentaMovimientos;

}
