package eterea.tenant.service.tenant.model.dto;

import eterea.tenant.service.tenant.kotlin.model.ValorMovimiento;
import eterea.tenant.service.tenant.model.CuentaMovimiento;
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
