package eterea.tenant.service.modules.contable.balance.domain.model;

import java.util.List;

/**
 * Resultado completo del balance: filas, totales y advertencias.
 *
 * <p>Es independiente del formato de salida: el reporte (PDF/XLSX) y la vista
 * previa se arman a partir de este mismo objeto.</p>
 */
public record Balance(
        List<BalanceCuenta> filas,
        BalanceTotales totales,
        BalanceFiltro filtro,
        List<String> advertencias) {

    public Balance {
        filas = List.copyOf(filas);
        advertencias = List.copyOf(advertencias);
    }

}
