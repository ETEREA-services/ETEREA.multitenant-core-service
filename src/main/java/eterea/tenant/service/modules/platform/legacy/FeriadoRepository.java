package eterea.tenant.service.modules.platform.legacy;

import eterea.tenant.service.modules.platform.legacy.Feriado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.OffsetDateTime;
import java.util.Optional;

@Repository
public interface FeriadoRepository extends JpaRepository<Feriado, OffsetDateTime> {

    Optional<Feriado> findByFecha(OffsetDateTime fecha);

}
