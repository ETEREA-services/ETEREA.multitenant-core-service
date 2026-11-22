package eterea.tenant.service.modules.contable.mayor.domain.ports.in;

import eterea.tenant.service.modules.contable.mayor.domain.model.MayorDiscriminado;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorDiscriminadoFiltro;

/**
 * Genera el Mayor Discriminado de varias cuentas.
 */
public interface GenerarMayorDiscriminadoUseCase {

    MayorDiscriminado generar(MayorDiscriminadoFiltro filtro);

}
