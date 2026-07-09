package eterea.tenant.service.tenant.hexagonal.invoicedata.domain.model;

import eterea.tenant.service.tenant.model.ClienteMovimiento;
import eterea.tenant.service.tenant.model.RegistroCae;
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
