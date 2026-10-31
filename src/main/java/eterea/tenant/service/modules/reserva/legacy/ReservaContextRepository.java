package eterea.tenant.service.modules.reserva.legacy;

import eterea.tenant.service.modules.reserva.legacy.ReservaContext;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface ReservaContextRepository extends JpaRepository<ReservaContext, Long> {

    Optional<ReservaContext> findByReservaContextId(long reservaContextId);

    List<ReservaContext> findAllByFacturaPendiente(byte facturaPendiente);

    Optional<ReservaContext> findByVoucherId(long voucherId);

    Optional<ReservaContext> findByReservaId(long reservaId);

}
