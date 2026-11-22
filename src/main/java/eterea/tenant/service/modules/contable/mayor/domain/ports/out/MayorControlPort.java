package eterea.tenant.service.modules.contable.mayor.domain.ports.out;

import eterea.tenant.service.modules.contable.mayor.domain.model.MayorControl;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorDiscriminadoFiltro;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorFiltro;

/**
 * Controles operativos del Mayor: total, excluidos, módulos y faltantes.
 */
public interface MayorControlPort {

    MayorControl findControl(int negocioId, MayorFiltro filtro);

    MayorControl findControlDiscriminado(int negocioId, MayorDiscriminadoFiltro filtro);

}
