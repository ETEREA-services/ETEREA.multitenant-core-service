/**
 * 
 */
package eterea.tenant.service.modules.reserva.legacy.repository;

import java.util.Optional;

import eterea.tenant.service.modules.reserva.legacy.ReservaOrigen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author daniel
 *
 */
@Repository
public interface ReservaOrigenRepository extends JpaRepository<ReservaOrigen, Integer> {

	Optional<ReservaOrigen> findByReservaOrigenId(Integer reservaOrigenId);

}
