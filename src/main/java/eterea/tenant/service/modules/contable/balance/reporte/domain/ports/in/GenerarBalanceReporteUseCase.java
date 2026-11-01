package eterea.tenant.service.modules.contable.balance.reporte.domain.ports.in;

import eterea.tenant.service.modules.contable.balance.domain.model.BalanceFiltro;
import eterea.tenant.service.modules.reporte.api.ReporteTabla;

/**
 * Arma el reporte del balance con el contrato común de reportes.
 */
public interface GenerarBalanceReporteUseCase {

    ReporteTabla generar(BalanceFiltro filtro);

}
