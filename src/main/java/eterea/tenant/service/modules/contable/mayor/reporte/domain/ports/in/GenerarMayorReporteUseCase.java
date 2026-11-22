package eterea.tenant.service.modules.contable.mayor.reporte.domain.ports.in;

import eterea.tenant.service.modules.contable.mayor.domain.model.MayorFiltro;
import eterea.tenant.service.modules.contable.mayor.reporte.domain.model.MayorReporte;

/**
 * Genera la tabla del Mayor General y devuelve también sus controles.
 */
public interface GenerarMayorReporteUseCase {

    MayorReporte generar(MayorFiltro filtro);

}
