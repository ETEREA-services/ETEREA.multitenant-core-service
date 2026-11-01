package eterea.tenant.service.modules.contable.balance.domain.ports.in;

import eterea.tenant.service.modules.contable.balance.domain.model.Balance;
import eterea.tenant.service.modules.contable.balance.domain.model.BalanceFiltro;

/**
 * Caso de uso de generación del balance.
 */
public interface GenerarBalanceUseCase {

    Balance generar(BalanceFiltro filtro);

}
