package eterea.tenant.service.tenant.model.dto;

import eterea.tenant.service.tenant.kotlin.model.ValorMovimiento;
import eterea.tenant.service.tenant.model.CuentaMovimiento;
import eterea.tenant.service.tool.Jsonifier;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferenciaWrapperDto {

    private TransferenciaDto transferencia;
    private List<ValorMovimiento> valorMovimientos;
    private List<CuentaMovimiento> cuentaMovimientos;

    public String jsonify() {
        return Jsonifier.builder(this).build();
    }

}
