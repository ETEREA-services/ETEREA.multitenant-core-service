package eterea.tenant.service.modules.tesoreria.legacy.hexagonal.transferencia.infrastructure.persistence.repository;

import eterea.tenant.service.modules.tesoreria.legacy.hexagonal.transferencia.infrastructure.persistence.entity.TransferenciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaTransferenciaRepository extends JpaRepository<TransferenciaEntity, Long> {

    Optional<TransferenciaEntity> findByNegocioIdDesdeAndNegocioIdHastaAndNumeroTransferencia(
            Integer negocioIdDesde,
            Integer negocioIdHasta,
            Long numeroTransferencia
    );

}
