package eterea.tenant.service.modules.platform.legacy;

import eterea.tenant.service.modules.platform.legacy.Parametro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ParametroRepository extends JpaRepository<Parametro, Integer> {

    Optional<Parametro> findTopByOrderByParametroIdDesc();

}
