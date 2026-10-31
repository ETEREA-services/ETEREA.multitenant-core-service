package eterea.tenant.service.modules.contable.cuenta.infrastructure.web.dto;

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
public class CuentaDetalleResponse {

    private Long numeroCuenta;
    private String nombre;
    private Integer negocioId;
    private boolean integra;
    private boolean transfiere;
    private boolean movimientoStock;
    private BigDecimal cuentaMaestro;
    private Integer grado;
    private Long grado1;
    private Long grado2;
    private Long grado3;
    private Long grado4;
    private Long padre;
    private Integer nivel;
    private boolean ventas;
    private boolean compras;
    private boolean gastos;
}
