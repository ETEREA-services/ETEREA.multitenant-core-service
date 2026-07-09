/**
 * 
 */
package eterea.tenant.service.tenant.repository;

import eterea.tenant.service.tenant.kotlin.model.HabitacionTipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author daniel
 *
 */
@Repository
public interface HabitacionTipoRepository extends JpaRepository<HabitacionTipo, Integer> {

}
