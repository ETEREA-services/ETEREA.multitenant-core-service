package eterea.tenant.service.tenant.hexagonal.transferencia.domain.ports.in;

import eterea.tenant.service.tenant.hexagonal.transferencia.domain.model.Transferencia;

public interface FindTransferenciaByUniqueUseCase {

    Transferencia findByUnique(Integer negocioIdDesde, Integer negocioIdHasta, Long numeroTransferencia);

}
