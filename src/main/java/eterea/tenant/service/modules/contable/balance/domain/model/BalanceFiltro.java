package eterea.tenant.service.modules.contable.balance.domain.model;

import eterea.tenant.service.modules.contable.balance.domain.exception.BalanceException;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Filtros del balance.
 *
 * <p>Las monedas se eligen en cada reporte: {@code monedaRegistroId} es la
 * moneda en la que están registrados los movimientos y {@code monedaExpresionId}
 * la moneda en la que se expresa el estado. Si no se envían, ambas son la
 * moneda base (ARS, {@code monedaId = 1}).</p>
 */
public record BalanceFiltro(
        LocalDate desde,
        LocalDate hasta,
        boolean incluyeApertura,
        boolean incluyeInflacion,
        BalanceModo modo,
        Integer monedaRegistroId,
        Integer monedaExpresionId) {

    public static final int MONEDA_BASE = 1;

    public BalanceFiltro {
        if (desde == null || hasta == null) {
            throw new BalanceException(
                    "BALANCE_RANGO_INVALIDO", "Las fechas desde y hasta son obligatorias.");
        }
        if (desde.isAfter(hasta)) {
            throw new BalanceException(
                    "BALANCE_RANGO_INVALIDO", "La fecha desde no puede ser posterior a la hasta.");
        }
        modo = modo == null ? BalanceModo.ESTRUCTURA : modo;
        monedaRegistroId = monedaRegistroId == null ? MONEDA_BASE : monedaRegistroId;
        monedaExpresionId = monedaExpresionId == null ? MONEDA_BASE : monedaExpresionId;
        if (monedaRegistroId <= 0 || monedaExpresionId <= 0) {
            throw new BalanceException(
                    "BALANCE_MONEDA_INVALIDA", "Las monedas deben ser identificadores positivos.");
        }
    }

    public boolean esMonedaBase() {
        return Objects.equals(monedaRegistroId, monedaExpresionId);
    }

}
