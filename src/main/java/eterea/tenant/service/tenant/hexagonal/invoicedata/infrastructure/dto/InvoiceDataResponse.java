package eterea.tenant.service.tenant.hexagonal.invoicedata.infrastructure.dto;

import eterea.tenant.service.tool.Jsonifier;
import eterea.tenant.service.tool.Jsonifyable;
import lombok.*;

@Getter
@Builder
public class InvoiceDataResponse implements Jsonifyable {

    ClienteMovimientoResponse clienteMovimiento;
    RegistroCaeResponse registroCae;
    ClienteMovimientoResponse clienteMovimientoAsociado;

}
