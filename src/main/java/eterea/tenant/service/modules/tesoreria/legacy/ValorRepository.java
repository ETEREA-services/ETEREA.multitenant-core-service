package eterea.tenant.service.modules.tesoreria.legacy;

import eterea.tenant.service.modules.tesoreria.legacy.Valor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ValorRepository extends JpaRepository<Valor, Integer> {

    Optional<Valor> findByValorId(int valorId);

}
