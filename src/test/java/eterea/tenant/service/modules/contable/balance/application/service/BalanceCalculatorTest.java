package eterea.tenant.service.modules.contable.balance.application.service;

import eterea.tenant.service.modules.contable.balance.domain.model.Balance;
import eterea.tenant.service.modules.contable.balance.domain.model.BalanceCuenta;
import eterea.tenant.service.modules.contable.balance.domain.model.BalanceFiltro;
import eterea.tenant.service.modules.contable.balance.domain.model.BalanceModo;
import eterea.tenant.service.modules.contable.balance.domain.model.SaldoCuenta;
import eterea.tenant.service.modules.contable.cuenta.domain.model.Cuenta;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Paridad con el Balance General de TSA Agencia del 31-10-2026.
 *
 * <p>Los importes de los movimientos salen de la base real: las 20 cuentas con
 * movimiento y sus débitos/créditos brutos. Los totales del PDF son la suma de
 * los saldos netos por cuenta (21.815.500,11 / 21.815.500,57).</p>
 */
class BalanceCalculatorTest {

    private final BalanceCalculator calculator = new BalanceCalculator();

    private static final LocalDate DIA = LocalDate.of(2026, 10, 31);

    @Test
    void reproduceElBalanceGeneralDelPdf() {
        Balance balance = calculator.calcular(
                cuentas(),
                movimientos(),
                List.of(),
                filtro(BalanceModo.ESTRUCTURA));

        assertThat(balance.filas()).hasSize(39);
        assertThat(balance.totales().deudores()).isEqualByComparingTo("21815500.11");
        assertThat(balance.totales().acreedores()).isEqualByComparingTo("21815500.57");
        assertThat(balance.totales().diferencia()).isEqualByComparingTo("-0.46");
        assertThat(balance.advertencias())
                .anyMatch(advertencia -> advertencia.contains("diferencia de -0.46"));

        assertFila(balance, 10000000L, "21815500.11", "0");
        assertFila(balance, 11000000L, "17119500.00", "0");
        assertFila(balance, 11203000L, "3010000.00", "0");
        assertFila(balance, 11203008L, "3010000.00", "0");
        assertFila(balance, 12000000L, "4696000.11", "0");
        assertFila(balance, 12101001L, "3140000.11", "0");
        assertFila(balance, 20000000L, "0", "3647318.23");
        assertFila(balance, 21201001L, "0", "3647318.23");
        assertFila(balance, 40000000L, "0", "18168182.34");
        assertFila(balance, 44100000L, "0", "18009504.63");
        assertFila(balance, 44106000L, "0", "2641323.02");
        assertFila(balance, 44501001L, "0", "9917.37");
    }

    @Test
    void reproduceElPorCuentaDelPdf() {
        Balance balance = calculator.calcular(
                cuentas(),
                movimientos(),
                List.of(),
                filtro(BalanceModo.POR_CUENTA));

        assertThat(balance.filas()).hasSize(20);
        assertThat(balance.totales().deudores()).isEqualByComparingTo("21815500.11");
        assertThat(balance.totales().acreedores()).isEqualByComparingTo("21815500.57");
        assertThat(balance.totales().diferencia()).isEqualByComparingTo("-0.46");
        assertThat(balance.filas()).allMatch(fila -> fila.nivel() == 1 && !fila.negrita());

        assertFila(balance, 11203008L, "3010000.00", "0");
        assertFila(balance, 21201001L, "0", "3647318.23");
        assertFila(balance, 44106000L, "0", "2641323.02");
    }

    @Test
    void sumaAlPadreElMovimientoPropioYLosHijos() {
        List<Cuenta> cuentas = List.of(
                cuenta(10000000L, "ACTIVO", 1),
                cuenta(11000000L, "DISPONIBILIDADES", 2),
                cuenta(11200000L, "OTRAS", 3));
        List<SaldoCuenta> saldos = List.of(
                new SaldoCuenta(11000000L, new BigDecimal("100.00"), BigDecimal.ZERO),
                new SaldoCuenta(11200000L, new BigDecimal("50.00"), BigDecimal.ZERO));

        Balance balance = calculator.calcular(
                cuentas, saldos, List.of(), filtro(BalanceModo.ESTRUCTURA));

        assertFila(balance, 10000000L, "150.00", "0");
        assertFila(balance, 11000000L, "150.00", "0");
        assertFila(balance, 11200000L, "50.00", "0");
    }

    @Test
    void incluyeAperturaSoloCuandoSePide() {
        List<SaldoCuenta> apertura = List.of(
                new SaldoCuenta(11203008L, new BigDecimal("10.00"), BigDecimal.ZERO));

        Balance sinApertura = calculator.calcular(
                cuentas(), movimientos(), List.of(), filtro(BalanceModo.ESTRUCTURA));
        Balance conApertura = calculator.calcular(
                cuentas(), movimientos(), apertura, filtroApertura());

        assertFila(sinApertura, 11203008L, "3010000.00", "0");
        assertFila(conApertura, 11203008L, "3010010.00", "0");
    }

    private static void assertFila(
            Balance balance, long cuenta, String deudor, String acreedor) {
        BalanceCuenta fila = balance.filas().stream()
                .filter(candidata -> candidata.numeroCuenta() == cuenta)
                .findFirst()
                .orElseThrow(() -> new AssertionError("No se encontró la cuenta " + cuenta));
        assertThat(fila.saldoDeudor()).isEqualByComparingTo(deudor);
        assertThat(fila.saldoAcreedor()).isEqualByComparingTo(acreedor);
    }

    private static BalanceFiltro filtro(BalanceModo modo) {
        return new BalanceFiltro(DIA, DIA, false, true, modo, 1, 1);
    }

    private static BalanceFiltro filtroApertura() {
        return new BalanceFiltro(DIA, DIA, true, true, BalanceModo.ESTRUCTURA, 1, 1);
    }

    private static Cuenta cuenta(long numero, String nombre, int grado) {
        return Cuenta.builder()
                .numeroCuenta(numero)
                .nombre(nombre)
                .grado(grado)
                .build();
    }

    private static List<Cuenta> cuentas() {
        List<Cuenta> cuentas = new ArrayList<>();
        cuentas.add(cuenta(10000000L, "ACTIVO", 1));
        cuentas.add(cuenta(11000000L, "DISPONIBILIDADES", 2));
        cuentas.add(cuenta(11200000L, "OTRAS DISPONIBILIDADES", 3));
        cuentas.add(cuenta(11203000L, "TARJETAS DE CRÉDITO", 4));
        cuentas.add(cuenta(11203008L, "Pago QR", 5));
        cuentas.add(cuenta(11205000L, "COBROS VIUMI", 4));
        cuentas.add(cuenta(11205002L, "Viumi Visa Crédito", 5));
        cuentas.add(cuenta(11205004L, "Viumi Master Crédito", 5));
        cuentas.add(cuenta(11206000L, "COBROS WEB CLIK DE PAGOS", 4));
        cuentas.add(cuenta(11206001L, "Web Visa Débito", 5));
        cuentas.add(cuenta(11206002L, "Web Visa Crédito", 5));
        cuentas.add(cuenta(11206003L, "Web Master Débito", 5));
        cuentas.add(cuenta(11206004L, "Web Master Crédito", 5));
        cuentas.add(cuenta(11206006L, "Web American Express Credito", 5));
        cuentas.add(cuenta(12000000L, "CREDITOS", 2));
        cuentas.add(cuenta(12100000L, "CREDITOS", 3));
        cuentas.add(cuenta(12101000L, "CREDITOS COMERCIALES", 4));
        cuentas.add(cuenta(12101001L, "Deudores por Ventas", 5));
        cuentas.add(cuenta(12101002L, "Reservas a Confirmar Agencia", 5));
        cuentas.add(cuenta(20000000L, "PASIVO", 1));
        cuentas.add(cuenta(21000000L, "DEUDAS", 2));
        cuentas.add(cuenta(21200000L, "DEUDAS FISCALES", 3));
        cuentas.add(cuenta(21201000L, "DEUDAS FISCALES", 4));
        cuentas.add(cuenta(21201001L, "IVA Débito Fiscal", 5));
        cuentas.add(cuenta(40000000L, "INGRESOS", 1));
        cuentas.add(cuenta(44000000L, "INGRESOS AGENCIA", 2));
        cuentas.add(cuenta(44100000L, "INGRESOS AGENCIA RECEPTIVO CAC", 3));
        cuentas.add(cuenta(44101000L, "Hotelería Cacheuta", 4));
        cuentas.add(cuenta(44102000L, "Transportes Cacheuta", 4));
        cuentas.add(cuenta(44103000L, "Comidas Cacheuta", 4));
        cuentas.add(cuenta(44106000L, "Parque de Agua Cacheuta", 4));
        cuentas.add(cuenta(44108000L, "S.P.A. Cacheuta", 4));
        cuentas.add(cuenta(44109000L, "Turismo Aventura Cacheuta", 4));
        cuentas.add(cuenta(44111000L, "Comida Parque de Agua", 4));
        cuentas.add(cuenta(44200000L, "INGRESOS AGENCIA RECEPTIVO MEN", 3));
        cuentas.add(cuenta(44205000L, "Excursiones Mendoza", 4));
        cuentas.add(cuenta(44500000L, "INGRESOS OTROS SERVICIOS", 3));
        cuentas.add(cuenta(44501000L, "INGRESOS PARQUE ESTACIONAMIENTO", 4));
        cuentas.add(cuenta(44501001L, "Ingresos Parque Estacionamiento Auto", 5));
        return cuentas;
    }

    private static List<SaldoCuenta> movimientos() {
        return List.of(
                saldo(11203008L, "3010000.00", "0"),
                saldo(11205002L, "2607500.00", "0"),
                saldo(11205004L, "850000.00", "0"),
                saldo(11206001L, "1383000.00", "0"),
                saldo(11206002L, "5592000.00", "0"),
                saldo(11206003L, "326000.00", "0"),
                saldo(11206004L, "2156000.00", "0"),
                saldo(11206006L, "1195000.00", "0"),
                saldo(12101001L, "3426457.37", "286457.26"),
                saldo(12101002L, "1556000.00", "0"),
                saldo(21201001L, "49715.72", "3697033.95"),
                saldo(44101000L, "0", "4143388.43"),
                saldo(44102000L, "0", "1599999.93"),
                saldo(44103000L, "0", "629752.03"),
                saldo(44106000L, "236741.54", "2878064.56"),
                saldo(44108000L, "0", "8342975.02"),
                saldo(44109000L, "0", "301652.90"),
                saldo(44111000L, "0", "350413.30"),
                saldo(44205000L, "0", "148760.34"),
                saldo(44501001L, "0", "9917.37"));
    }

    private static SaldoCuenta saldo(long cuenta, String debe, String haber) {
        return new SaldoCuenta(cuenta, new BigDecimal(debe), new BigDecimal(haber));
    }

}
