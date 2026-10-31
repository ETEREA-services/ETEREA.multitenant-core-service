/**
 * 
 */
package eterea.tenant.service.modules.reserva.legacy.repository;

import eterea.tenant.service.modules.reserva.legacy.HabitacionTipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author daniel
 *
 */
@Repository
public interface HabitacionTipoRepository extends JpaRepository<HabitacionTipo, Integer> {

}
