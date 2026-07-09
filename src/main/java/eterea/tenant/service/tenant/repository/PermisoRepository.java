/**
 * 
 */
package eterea.tenant.service.tenant.repository;

import java.util.Optional;

import eterea.tenant.service.tenant.kotlin.model.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author daniel
 *
 */
@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Long> {

	Optional<Permiso> findByNombreAndOpcion(String usuario, String opcion);

}
