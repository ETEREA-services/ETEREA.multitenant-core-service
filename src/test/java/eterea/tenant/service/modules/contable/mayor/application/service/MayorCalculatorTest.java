package eterea.tenant.service.modules.contable.mayor.application.service;

import eterea.tenant.service.modules.contable.cuenta.domain.model.Cuenta;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorControl;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorCuenta;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorDetalle;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorDiscriminadoFila;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorModulo;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MayorCalculatorTest {

    private static final LocalDate DIA = LocalDate.of(2026, 11, 11);

    private final MayorCalculator calculator = new MayorCalculator();

    @Test
    void calculaSaldoCorridoYtotales() {
        MayorCuenta mayor = calculator.calcular(
                cuenta(),
                new BigDecimal("100.00"),
                List.of(
                        detalle("50.00", "0.00"),
                        detalle("0.00", "30.00"),
                        detalle("0.00", "20.00")),
                MayorControl.vacio());

        assertThat(mayor.saldoAnterior()).isEqualByComparingTo("100.00");
        assertThat(mayor.movimientos())
                .extracting(movimiento -> movimiento.saldo().toPlainString())
                .containsExactly("150.00", "120.00", "100.00");
        assertThat(mayor.totalDebe()).isEqualByComparingTo("50.00");
        assertThat(mayor.totalHaber()).isEqualByComparingTo("50.00");
        assertThat(mayor.saldoFinal()).isEqualByComparingTo("100.00");
        assertThat(mayor.numeroCuenta()).isEqualTo(21201001L);
        assertThat(mayor.nombre()).isEqualTo("IVA Débito Fiscal");
    }

    @Test
    void sinSaldoAnteriorArrancaEnCero() {
        MayorCuenta mayor = calculator.calcular(
                cuenta(),
                BigDecimal.ZERO,
                List.of(detalle("10.00", "0.00"), detalle("0.00", "4.00")),
                MayorControl.vacio());

        assertThat(mayor.saldoAnterior()).isEqualByComparingTo("0");
        assertThat(mayor.movimientos().getFirst().saldo()).isEqualByComparingTo("10.00");
        assertThat(mayor.saldoFinal()).isEqualByComparingTo("6.00");
    }

    @Test
    void redondeaHaciaArribaConHalfUp() {
        MayorCuenta mayor = calculator.calcular(
                cuenta(),
                BigDecimal.ZERO,
                List.of(detalle("10.005", "0.005")),
                MayorControl.vacio());

        assertThat(mayor.movimientos().getFirst().debe()).isEqualByComparingTo("10.01");
        assertThat(mayor.movimientos().getFirst().haber()).isEqualByComparingTo("0.01");
        assertThat(mayor.saldoFinal()).isEqualByComparingTo("10.00");
    }

    @Test
    void permiteSaldoNegativo() {
        MayorCuenta mayor = calculator.calcular(
                cuenta(),
                new BigDecimal("-20.00"),
                List.of(detalle("0.00", "5.00")),
                MayorControl.vacio());

        assertThat(mayor.saldoFinal()).isEqualByComparingTo("-25.00");
    }

    @Test
    void sinMovimientosConservaElSaldoAnterior() {
        MayorCuenta mayor = calculator.calcular(
                cuenta(),
                new BigDecimal("250.00"),
                List.of(),
                MayorControl.vacio());

        assertThat(mayor.movimientos()).isEmpty();
        assertThat(mayor.totalDebe()).isEqualByComparingTo("0");
        assertThat(mayor.totalHaber()).isEqualByComparingTo("0");
        assertThat(mayor.saldoFinal()).isEqualByComparingTo("250.00");
    }

    @Test
    void reproduceLasPrimerasFilasDelOraculoO1() {
        MayorCuenta mayor = calculator.calcular(
                cuenta(),
                new BigDecimal("-39691926.90"),
                List.of(
                        detalle(42, "Fact.B Pto 34 Cdo", "Nro: 0034 215886", "0", "8504.13"),
                        detalle(43, "Fact.B Pto 34 Cdo", "Nro: 0034 215887", "0", "5900.83"),
                        detalle(44, "Fact.A Pto 37 Cta Cte", "Nro: 0037 006450", "0", "4720.66")),
                MayorControl.vacio());

        assertThat(mayor.movimientos())
                .extracting(movimiento -> movimiento.saldo().toPlainString())
                .containsExactly("-39700431.03", "-39706331.86", "-39711052.52");
        assertThat(mayor.totalHaber()).isEqualByComparingTo("19125.62");
        assertThat(mayor.saldoFinal()).isEqualByComparingTo("-39711052.52");
    }

    @Test
    void reproduceLosTotalesDelOraculoO1() {
        MayorCuenta mayor = calculator.calcular(
                cuenta(),
                new BigDecimal("-39691926.90"),
                List.of(
                        detalle(1, "Varios", "Debe del día", "89201.86", "0.00"),
                        detalle(2, "Varios", "Haber del día", "0.00", "6034132.66")),
                MayorControl.vacio());

        assertThat(mayor.totalDebe()).isEqualByComparingTo("89201.86");
        assertThat(mayor.totalHaber()).isEqualByComparingTo("6034132.66");
        assertThat(mayor.saldoFinal()).isEqualByComparingTo("-45636857.70");
    }

    @Test
    void reproduceLosTotalesDelOraculoO2() {
        MayorCuenta mayor = calculator.calcular(
                cuenta(),
                new BigDecimal("-728391.34"),
                List.of(
                        detalle(1, "Varios", "Debe del mes", "3815465.33", "0.00"),
                        detalle(2, "Varios", "Haber del mes", "0.00", "86267498.17")),
                MayorControl.vacio());

        assertThat(mayor.totalDebe()).isEqualByComparingTo("3815465.33");
        assertThat(mayor.totalHaber()).isEqualByComparingTo("86267498.17");
        assertThat(mayor.saldoFinal()).isEqualByComparingTo("-83180424.18");
    }

    @Test
    void conservaElControlRecibido() {
        MayorControl control = new MayorControl(
                3,
                1,
                List.of(new MayorModulo(1, "Ventas", 2)),
                List.of(141));

        MayorCuenta mayor = calculator.calcular(
                cuenta(), BigDecimal.ZERO, List.of(), control);

        assertThat(mayor.control()).isSameAs(control);
    }

    @Test
    void redondeaElDiscriminadoADosDecimales() {
        List<MayorDiscriminadoFila> filas = calculator.calcularDiscriminado(List.of(
                new MayorDiscriminadoFila(
                        DIA, 21201001L, "IVA Débito Fiscal", "Factura",
                        "Nro: 1", 42, new BigDecimal("10.005"),
                        new BigDecimal("0.005"), "Proveedor")));

        assertThat(filas).hasSize(1);
        assertThat(filas.getFirst().debe()).isEqualByComparingTo("10.01");
        assertThat(filas.getFirst().haber()).isEqualByComparingTo("0.01");
    }

    private static Cuenta cuenta() {
        return Cuenta.builder()
                .numeroCuenta(21201001L)
                .nombre("IVA Débito Fiscal")
                .negocioId(54)
                .build();
    }

    private static MayorDetalle detalle(String debe, String haber) {
        return detalle(42, "Factura", "Concepto", debe, haber);
    }

    private static MayorDetalle detalle(
            int asiento, String comprobante, String concepto, String debe, String haber) {
        return new MayorDetalle(
                DIA,
                1,
                comprobante,
                concepto,
                asiento,
                new BigDecimal(debe),
                new BigDecimal(haber),
                0L,
                "");
    }

}
