/**
 * 
 */
package eterea.tenant.service.modules.reserva.legacy.repository;

import java.util.Optional;

import eterea.tenant.service.modules.reserva.legacy.HabitacionTarifa;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author daniel
 *
 */
public interface HabitacionTarifaRepository extends JpaRepository<HabitacionTarifa, Long> {

	Optional<HabitacionTarifa> findByNumeroAndPaxs(Integer numero, Integer paxs);

	Optional<HabitacionTarifa> findByHabitacionTarifaId(Long habitacionTarifaId);

}
