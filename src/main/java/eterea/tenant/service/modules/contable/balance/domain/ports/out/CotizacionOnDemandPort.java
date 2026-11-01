package eterea.tenant.service.modules.contable.balance.domain.ports.out;

import java.time.LocalDate;
import java.util.List;

/**
 * Obtiene tasas de BCRA sólo cuando el reporte las necesita.
 *
 * <p>El puerto recibe el par y las fechas que no tienen cobertura; el
 * adaptador pide las monedas involucradas, calcula el coeficiente y hace
 * upsert en {@code moneda_cotizacion}. No toca datos contables.</p>
 */
public interface CotizacionOnDemandPort {

    void asegurar(
            int negocioId,
            Integer monedaRegistroId,
            Integer monedaExpresionId,
            LocalDate desde,
            LocalDate hasta,
            List<LocalDate> fechasFaltantes);

}
