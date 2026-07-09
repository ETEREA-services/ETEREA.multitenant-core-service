package eterea.tenant.service.unificado.hexagonal.negocioUnificado.infrastructure.persistence.repository;

import eterea.tenant.service.unificado.hexagonal.negocioUnificado.infrastructure.persistence.entity.NegocioUnificadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaNegocioUnificadoRepository extends JpaRepository<NegocioUnificadoEntity, Integer> {

    Optional<NegocioUnificadoEntity> findByNegocioId(Integer negocioId);

}
