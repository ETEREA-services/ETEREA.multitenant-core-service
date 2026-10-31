package eterea.tenant.service.modules.tesoreria.legacy.hexagonal.transferencia.domain.ports.out;

import eterea.tenant.service.modules.tesoreria.legacy.hexagonal.transferencia.domain.model.Transferencia;

import java.util.Optional;

public interface TransferenciaRepository {

    Optional<Transferencia> findByNegocioIdDesdeAndNegocioIdHastaAndNumeroTransferencia(
            Integer negocioIdDesde,
            Integer negocioIdHasta,
            Long numeroTransferencia
    );

}
