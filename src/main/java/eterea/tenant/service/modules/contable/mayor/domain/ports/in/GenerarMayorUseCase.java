package eterea.tenant.service.modules.contable.mayor.domain.ports.in;

import eterea.tenant.service.modules.contable.mayor.domain.model.MayorCuenta;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorFiltro;

/**
 * Genera el Mayor de una cuenta.
 */
public interface GenerarMayorUseCase {

    MayorCuenta generar(MayorFiltro filtro);

}
