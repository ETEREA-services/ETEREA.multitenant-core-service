package eterea.tenant.service.modules.contable.balance.domain.ports.out;

import eterea.tenant.service.modules.contable.balance.domain.model.MonedaInfo;

import java.util.List;
import java.util.Optional;

/**
 * Catálogo de monedas del tenant.
 */
public interface MonedaQueryPort {

    List<MonedaInfo> findAll();

    Optional<MonedaInfo> findById(Integer monedaId);

}
