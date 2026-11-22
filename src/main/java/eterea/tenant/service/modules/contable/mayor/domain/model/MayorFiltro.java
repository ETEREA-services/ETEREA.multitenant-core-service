package eterea.tenant.service.modules.contable.mayor.domain.model;

import eterea.tenant.service.modules.contable.mayor.domain.exception.MayorException;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Filtros del Mayor de una cuenta.
 *
 * <p>Las monedas se eligen por reporte. Si no se envían, ambas son la moneda
 * base (ARS, {@code monedaId = 1}). El saldo anterior se convierte con la tasa
 * de {@code desde - 1} (D10-A).</p>
 */
public record MayorFiltro(
        long cuenta,
        LocalDate desde,
        LocalDate hasta,
        boolean sinSaldoAnterior,
        Integer monedaRegistroId,
        Integer monedaExpresionId) {

    public static final int MONEDA_BASE = 1;

    public MayorFiltro {
        if (cuenta <= 0) {
            throw new MayorException(
                    "MAYOR_CUENTA_REQUERIDA", "La cuenta es obligatoria.");
        }
        if (desde == null || hasta == null) {
            throw new MayorException(
                    "MAYOR_RANGO_INVALIDO", "Las fechas desde y hasta son obligatorias.");
        }
        if (desde.isAfter(hasta)) {
            throw new MayorException(
                    "MAYOR_RANGO_INVALIDO", "La fecha desde no puede ser posterior a la hasta.");
        }
        monedaRegistroId = monedaRegistroId == null ? MONEDA_BASE : monedaRegistroId;
        monedaExpresionId = monedaExpresionId == null ? MONEDA_BASE : monedaExpresionId;
        if (monedaRegistroId <= 0 || monedaExpresionId <= 0) {
            throw new MayorException(
                    "MAYOR_MONEDA_INVALIDA", "Las monedas deben ser identificadores positivos.");
        }
    }

    public boolean esMonedaBase() {
        return Objects.equals(monedaRegistroId, monedaExpresionId);
    }

    /**
     * Fecha usada para convertir el saldo anterior (D10-A).
     */
    public LocalDate fechaApertura() {
        return desde.minusDays(1);
    }

}
