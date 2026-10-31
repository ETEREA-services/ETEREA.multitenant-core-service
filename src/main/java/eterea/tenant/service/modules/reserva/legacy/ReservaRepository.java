package eterea.tenant.service.modules.reserva.legacy;

import eterea.tenant.service.modules.reserva.legacy.Reserva;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    Optional<Reserva> findByReservaId(Long reservaId);

    List<Reserva> findTop100ByVerificadaAndFacturadaAndEliminadaAndPagaCacheutaAndFacturadoFueraAndAnuladaAndClienteIdGreaterThan(Byte verificada, Byte facturada, Byte eliminada, Byte pagaCacheuta, Byte facturadoFuera, Byte anulada, Long clienteId, Sort sort);

}
