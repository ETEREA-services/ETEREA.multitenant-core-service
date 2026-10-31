package eterea.tenant.service.modules.ventas.legacy.hexagonal.invoicedata.domain.model;

import eterea.tenant.service.modules.ventas.legacy.model.ClienteMovimiento;
import eterea.tenant.service.modules.ventas.legacy.RegistroCae;
import eterea.tenant.service.tool.Jsonifier;
import eterea.tenant.service.tool.Jsonifyable;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceData implements Jsonifyable {

    ClienteMovimiento clienteMovimiento;
    RegistroCae registroCae;
    ClienteMovimiento clienteMovimientoAsociado;

}
