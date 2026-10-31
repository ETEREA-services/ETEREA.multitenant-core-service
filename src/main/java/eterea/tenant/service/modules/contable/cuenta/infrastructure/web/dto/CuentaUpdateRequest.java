package eterea.tenant.service.modules.contable.cuenta.infrastructure.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CuentaUpdateRequest {

    @NotBlank
    @Size(max = 200)
    private String nombre;

    @DecimalMin("0")
    @Digits(integer = 11, fraction = 0)
    private BigDecimal cuentaMaestro;

    private boolean transfiere;
    private boolean movimientoStock;
    private boolean ventas;
    private boolean compras;
    private boolean gastos;
}
