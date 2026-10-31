package eterea.tenant.service.modules.contable.cuenta.reporte.domain.ports.in;

import eterea.tenant.service.modules.reporte.api.ReporteTabla;

/**
 * Caso de uso del listado del plan de cuentas del negocio activo.
 */
public interface GenerarPlanCuentasReporteUseCase {

    ReporteTabla generar();

}
