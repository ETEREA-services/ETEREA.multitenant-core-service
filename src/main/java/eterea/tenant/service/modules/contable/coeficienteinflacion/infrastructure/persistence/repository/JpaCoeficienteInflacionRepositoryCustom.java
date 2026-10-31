package eterea.tenant.service.modules.contable.coeficienteinflacion.infrastructure.persistence.repository;

import java.util.List;

public interface JpaCoeficienteInflacionRepositoryCustom {

    List<Integer> findAniosDistintos();

}
