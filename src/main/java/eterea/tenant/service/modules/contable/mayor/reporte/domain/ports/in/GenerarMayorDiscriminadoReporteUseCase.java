package eterea.tenant.service.modules.contable.mayor.reporte.domain.ports.in;

import eterea.tenant.service.modules.contable.mayor.domain.model.MayorDiscriminadoFiltro;
import eterea.tenant.service.modules.reporte.api.ReporteTabla;

/**
 * Genera la tabla del Mayor Discriminado.
 */
public interface GenerarMayorDiscriminadoReporteUseCase {

    ReporteTabla generar(MayorDiscriminadoFiltro filtro);

}
