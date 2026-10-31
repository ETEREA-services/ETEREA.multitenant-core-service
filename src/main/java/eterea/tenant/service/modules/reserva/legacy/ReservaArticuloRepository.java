package eterea.tenant.service.modules.reserva.legacy;

import eterea.tenant.service.modules.reserva.legacy.ReservaArticulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface ReservaArticuloRepository extends JpaRepository<ReservaArticulo, Long> {

    List<ReservaArticulo> findAllByReservaId(long reservaId);

    List<ReservaArticulo> findAllByReservaIdAndVoucherId(long reservaId, long voucherId);

    Optional<ReservaArticulo> findByReservaArticuloId(long reservaArticuloId);

    void deleteByReservaArticuloId(long reservaArticuloId);

}
