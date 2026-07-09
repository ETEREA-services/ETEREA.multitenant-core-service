package eterea.tenant.service.tenant.model.dto;

import eterea.tenant.service.tenant.kotlin.model.ReservaOrigen;
import eterea.tenant.service.tenant.kotlin.model.Voucher;
import eterea.tenant.service.tenant.model.ClienteMovimiento;
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
