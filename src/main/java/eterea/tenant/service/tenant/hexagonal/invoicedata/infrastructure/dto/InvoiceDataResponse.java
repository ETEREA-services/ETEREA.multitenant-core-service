package eterea.tenant.service.tenant.hexagonal.invoicedata.infrastructure.dto;

import eterea.tenant.service.tool.Jsonifier;
import lombok.*;

@Getter
@Builder
public class InvoiceDataResponse {

    ClienteMovimientoResponse clienteMovimiento;
    RegistroCaeResponse registroCae;
    ClienteMovimientoResponse clienteMovimientoAsociado;

    public String jsonify() {
        return Jsonifier.builder(this).build();
    }

}
