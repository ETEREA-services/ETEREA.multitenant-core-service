package eterea.tenant.service.modules.contable.mayor.domain.model;

import eterea.tenant.service.modules.contable.mayor.domain.exception.MayorException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Filtros del Mayor Discriminado: varias cuentas y un rango.
 */
public record MayorDiscriminadoFiltro(
        List<Long> cuentas,
        LocalDate desde,
        LocalDate hasta,
        Integer monedaRegistroId,
        Integer monedaExpresionId) {

    public MayorDiscriminadoFiltro {
        cuentas = cuentas == null
                ? List.of()
                : cuentas.stream().filter(Objects::nonNull).distinct().toList();
        if (cuentas.isEmpty()) {
            throw new MayorException(
                    "MAYOR_CUENTAS_REQUERIDAS", "Hay que seleccionar al menos una cuenta.");
        }
        if (cuentas.stream().anyMatch(cuenta -> cuenta <= 0)) {
            throw new MayorException(
                    "MAYOR_CUENTA_REQUERIDA", "Todas las cuentas deben ser positivas.");
        }
        if (desde == null || hasta == null) {
            throw new MayorException(
                    "MAYOR_RANGO_INVALIDO", "Las fechas desde y hasta son obligatorias.");
        }
        if (desde.isAfter(hasta)) {
            throw new MayorException(
                    "MAYOR_RANGO_INVALIDO", "La fecha desde no puede ser posterior a la hasta.");
        }
        monedaRegistroId = monedaRegistroId == null ? MayorFiltro.MONEDA_BASE : monedaRegistroId;
        monedaExpresionId = monedaExpresionId == null
                ? MayorFiltro.MONEDA_BASE : monedaExpresionId;
        if (monedaRegistroId <= 0 || monedaExpresionId <= 0) {
            throw new MayorException(
                    "MAYOR_MONEDA_INVALIDA", "Las monedas deben ser identificadores positivos.");
        }
    }

    public boolean esMonedaBase() {
        return Objects.equals(monedaRegistroId, monedaExpresionId);
    }

}
