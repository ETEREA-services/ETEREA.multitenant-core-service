package eterea.tenant.service.modules.ventas.legacy.model.dto;

import eterea.tenant.service.modules.reserva.legacy.ReservaOrigen;
import eterea.tenant.service.modules.ventas.legacy.Voucher;
import eterea.tenant.service.modules.ventas.legacy.model.ClienteMovimiento;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramaDiaDto {

    private List<Voucher> vouchers;
    private List<ReservaOrigen> reservaOrigens;
    private List<ClienteMovimiento> clienteMovimientos;
    @Builder.Default
    private String errorMessage = "";

}
