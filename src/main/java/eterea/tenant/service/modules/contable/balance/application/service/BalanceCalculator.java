package eterea.tenant.service.modules.contable.balance.application.service;

import eterea.tenant.service.modules.contable.balance.domain.model.Balance;
import eterea.tenant.service.modules.contable.balance.domain.model.BalanceCuenta;
import eterea.tenant.service.modules.contable.balance.domain.model.BalanceFiltro;
import eterea.tenant.service.modules.contable.balance.domain.model.BalanceModo;
import eterea.tenant.service.modules.contable.balance.domain.model.BalanceTotales;
import eterea.tenant.service.modules.contable.balance.domain.model.SaldoCuenta;
import eterea.tenant.service.modules.contable.cuenta.domain.model.CodigoCuenta;
import eterea.tenant.service.modules.contable.cuenta.domain.model.Cuenta;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Calcula el balance a partir del plan de cuentas y de los agregados de
 * movimientos.
 *
 * <p>El cálculo es O(n): se acumulan los saldos propios y se suben al padre en
 * orden descendente de código. No usa los campos {@code gradoN} del plan para
 * la jerarquía: los deriva de {@link CodigoCuenta}, igual que el legacy.</p>
 *
 * <p>El saldo neto de cada cuenta se redondea a 2 decimales antes de totalizar,
 * replicando {@code a2Decimales} del legacy. La conversión de moneda ya viene
 * redondeada a 5 decimales desde el adaptador.</p>
 */
@Component
public class BalanceCalculator {

    private static final int ESCALA_SALDO = 2;
    private static final int NIVEL_HOJA = 5;

    public Balance calcular(
            List<Cuenta> cuentas,
            List<SaldoCuenta> movimientos,
            List<SaldoCuenta> apertura,
            BalanceFiltro filtro) {

        Map<Long, Cuenta> plan = new HashMap<>();
        Map<Long, BigDecimal[]> propios = new TreeMap<>();
        List<String> advertencias = new ArrayList<>();

        for (Cuenta cuenta : cuentas) {
            plan.put(cuenta.getNumeroCuenta(), cuenta);
            propios.put(cuenta.getNumeroCuenta(), new BigDecimal[]{
                    BigDecimal.ZERO, BigDecimal.ZERO});
        }

        acumular(movimientos, propios, plan, advertencias);
        if (filtro.incluyeApertura()) {
            acumular(apertura, propios, plan, advertencias);
        }

        Map<Long, BigDecimal[]> totales = new TreeMap<>();
        for (Map.Entry<Long, BigDecimal[]> entrada : propios.entrySet()) {
            totales.put(entrada.getKey(), new BigDecimal[]{
                    entrada.getValue()[0], entrada.getValue()[1]});
        }

        for (Long numeroCuenta : new ArrayList<>(totales.keySet()).reversed()) {
            long padre = CodigoCuenta.of(numeroCuenta).padre();
            if (padre == 0L) {
                continue;
            }
            BigDecimal[] totalCuenta = totales.get(numeroCuenta);
            BigDecimal[] totalPadre = totales.get(padre);
            if (totalPadre == null || totalCuenta == null) {
                continue;
            }
            totalPadre[0] = totalPadre[0].add(totalCuenta[0]);
            totalPadre[1] = totalPadre[1].add(totalCuenta[1]);
        }

        List<BalanceCuenta> filas = new ArrayList<>();
        if (filtro.modo() == BalanceModo.POR_CUENTA) {
            construirPorCuenta(propios, plan, filas);
        } else {
            construirEstructura(totales, plan, filas);
        }

        BalanceTotales totalesBalance = totalizar(propios, filtro, filas);
        if (totalesBalance.diferencia().signum() != 0) {
            advertencias.add("El balance no cuadra: diferencia de "
                    + totalesBalance.diferencia().setScale(2, RoundingMode.HALF_UP).toPlainString()
                    + ".");
        }

        return new Balance(filas, totalesBalance, filtro, advertencias);
    }

    private void acumular(
            List<SaldoCuenta> saldos,
            Map<Long, BigDecimal[]> propios,
            Map<Long, Cuenta> plan,
            List<String> advertencias) {
        for (SaldoCuenta saldo : saldos) {
            BigDecimal[] acumulado = propios.get(saldo.numeroCuenta());
            if (acumulado == null) {
                advertencias.add("La cuenta " + saldo.numeroCuenta()
                        + " tiene movimientos pero no está en el plan.");
                continue;
            }
            acumulado[0] = acumulado[0].add(saldo.debe());
            acumulado[1] = acumulado[1].add(saldo.haber());
        }
    }

    private void construirEstructura(
            Map<Long, BigDecimal[]> totales,
            Map<Long, Cuenta> plan,
            List<BalanceCuenta> filas) {
        for (Map.Entry<Long, BigDecimal[]> entrada : totales.entrySet()) {
            BigDecimal debe = entrada.getValue()[0];
            BigDecimal haber = entrada.getValue()[1];
            if (debe.signum() == 0 && haber.signum() == 0) {
                continue;
            }
            Cuenta cuenta = plan.get(entrada.getKey());
            int nivel = nivel(entrada.getKey());
            BigDecimal saldo = saldo(debe, haber);
            filas.add(new BalanceCuenta(
                    entrada.getKey(),
                    cuenta == null ? "" : cuenta.getNombre(),
                    nivel,
                    nivel < NIVEL_HOJA,
                    debe,
                    haber,
                    saldo.max(BigDecimal.ZERO),
                    saldo.negate().max(BigDecimal.ZERO)));
        }
        filas.sort(Comparator.comparingLong(BalanceCuenta::numeroCuenta));
    }

    private void construirPorCuenta(
            Map<Long, BigDecimal[]> propios,
            Map<Long, Cuenta> plan,
            List<BalanceCuenta> filas) {
        for (Map.Entry<Long, BigDecimal[]> entrada : propios.entrySet()) {
            BigDecimal debe = entrada.getValue()[0];
            BigDecimal haber = entrada.getValue()[1];
            if (debe.signum() == 0 && haber.signum() == 0) {
                continue;
            }
            Cuenta cuenta = plan.get(entrada.getKey());
            BigDecimal saldo = saldo(debe, haber);
            filas.add(new BalanceCuenta(
                    entrada.getKey(),
                    cuenta == null ? "" : cuenta.getNombre(),
                    1,
                    false,
                    debe,
                    haber,
                    saldo.max(BigDecimal.ZERO),
                    saldo.negate().max(BigDecimal.ZERO)));
        }
        filas.sort(Comparator.comparingLong(BalanceCuenta::numeroCuenta));
    }

    private BalanceTotales totalizar(
            Map<Long, BigDecimal[]> propios,
            BalanceFiltro filtro,
            List<BalanceCuenta> filas) {
        BigDecimal deudores = BigDecimal.ZERO;
        BigDecimal acreedores = BigDecimal.ZERO;

        if (filtro.modo() == BalanceModo.POR_CUENTA) {
            for (BalanceCuenta fila : filas) {
                deudores = deudores.add(fila.saldoDeudor());
                acreedores = acreedores.add(fila.saldoAcreedor());
            }
        } else {
            for (BigDecimal[] propio : propios.values()) {
                BigDecimal saldo = saldo(propio[0], propio[1]);
                deudores = deudores.add(saldo.max(BigDecimal.ZERO));
                acreedores = acreedores.add(saldo.negate().max(BigDecimal.ZERO));
            }
        }

        deudores = deudores.setScale(ESCALA_SALDO, RoundingMode.HALF_UP);
        acreedores = acreedores.setScale(ESCALA_SALDO, RoundingMode.HALF_UP);
        return new BalanceTotales(deudores, acreedores, deudores.subtract(acreedores));
    }

    private static BigDecimal saldo(BigDecimal debe, BigDecimal haber) {
        return debe.subtract(haber).setScale(ESCALA_SALDO, RoundingMode.HALF_UP);
    }

    private static int nivel(long numeroCuenta) {
        try {
            return CodigoCuenta.of(numeroCuenta).nivel();
        } catch (RuntimeException exception) {
            return NIVEL_HOJA;
        }
    }

}
