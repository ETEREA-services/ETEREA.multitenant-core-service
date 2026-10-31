package eterea.tenant.service.modules.contable.cuenta.infrastructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CuentaNodoResponse {

    private CuentaDetalleResponse cuenta;
    private boolean huerfana;
    private List<CuentaNodoResponse> hijos;
}
