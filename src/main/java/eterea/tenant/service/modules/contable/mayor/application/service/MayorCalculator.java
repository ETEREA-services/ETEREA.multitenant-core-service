package eterea.tenant.service.modules.contable.mayor.application.service;

import eterea.tenant.service.modules.contable.cuenta.domain.model.Cuenta;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorControl;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorCuenta;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorDetalle;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorDiscriminadoFila;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorMovimiento;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Calcula el saldo corrido y los totales del Mayor.
 *
 * <p>Los importes visibles se redondean a 2 decimales con {@link RoundingMode#HALF_UP}.
 * El legacy usa {@code a2Decimales} ({@code Format(valor, "0.00")}); el modo
 * exacto se valida contra los oráculos en M5.</p>
 */
@Component
public class MayorCalculator {

    private static final int ESCALA = 2;

    public MayorCuenta calcular(
            Cuenta cuenta,
            BigDecimal saldoAnterior,
            List<MayorDetalle> detalles,
            MayorControl control) {

        BigDecimal saldo = redondear(saldoAnterior);
        BigDecimal totalDebe = BigDecimal.ZERO;
        BigDecimal totalHaber = BigDecimal.ZERO;
        List<MayorMovimiento> movimientos = new ArrayList<>(detalles.size());

        for (MayorDetalle detalle : detalles) {
            BigDecimal debe = redondear(detalle.debe());
            BigDecimal haber = redondear(detalle.haber());
            saldo = redondear(saldo.add(debe).subtract(haber));
            totalDebe = totalDebe.add(debe);
            totalHaber = totalHaber.add(haber);
            movimientos.add(new MayorMovimiento(
                    detalle.fecha(),
                    detalle.comprobante(),
                    detalle.concepto(),
                    detalle.asiento(),
                    debe,
                    haber,
                    saldo,
                    detalle.proveedor()));
        }

        return new MayorCuenta(
                cuenta.getNumeroCuenta(),
                cuenta.getNombre(),
                redondear(saldoAnterior),
                movimientos,
                totalDebe,
                totalHaber,
                saldo,
                control == null ? MayorControl.vacio() : control);
    }

    /**
     * Normaliza los importes del Discriminado a 2 decimales para que el
     * dominio y el reporte trabajen con la misma escala.
     */
    public List<MayorDiscriminadoFila> calcularDiscriminado(List<MayorDiscriminadoFila> filas) {
        return filas.stream()
                .map(fila -> new MayorDiscriminadoFila(
                        fila.fecha(),
                        fila.numeroCuenta(),
                        fila.nombreCuenta(),
                        fila.comprobante(),
                        fila.concepto(),
                        fila.asiento(),
                        redondear(fila.debe()),
                        redondear(fila.haber()),
                        fila.proveedor()))
                .toList();
    }

    private static BigDecimal redondear(BigDecimal valor) {
        return (valor == null ? BigDecimal.ZERO : valor)
                .setScale(ESCALA, RoundingMode.HALF_UP);
    }

}
