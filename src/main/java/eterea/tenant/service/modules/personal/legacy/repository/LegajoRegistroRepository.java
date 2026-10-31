package eterea.tenant.service.modules.personal.legacy.repository;

import eterea.tenant.service.modules.personal.legacy.LegajoRegistro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegajoRegistroRepository
        extends JpaRepository<LegajoRegistro, Long>, LegajoRegistroRepositoryCustom {

    List<LegajoRegistro> findAllByLegajoId(Integer legajoId);

}
