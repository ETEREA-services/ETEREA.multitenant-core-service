package eterea.tenant.service.modules.contable.coeficienteinflacion.infrastructure.persistence.repository;

import eterea.tenant.service.modules.contable.coeficienteinflacion.infrastructure.persistence.entity.CoeficienteInflacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaCoeficienteInflacionRepository
        extends JpaRepository<CoeficienteInflacionEntity, Long>,
        JpaCoeficienteInflacionRepositoryCustom {

    List<CoeficienteInflacionEntity> findAllByAnhoOrderByMesAsc(Integer anho);

    Optional<CoeficienteInflacionEntity>
    findFirstByAnhoAndMesOrderByCoeficienteInflacionIdDesc(Integer anho, Integer mes);

}
