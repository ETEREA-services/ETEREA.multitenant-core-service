/**
 * 
 */
package eterea.tenant.service.modules.ventas.legacy.repository;

import java.util.Optional;

import eterea.tenant.service.modules.ventas.legacy.ComprobanteAfip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author daniel
 *
 */
@Repository
public interface ComprobanteAfipRepository extends JpaRepository<ComprobanteAfip, Integer> {

	Optional<ComprobanteAfip> findByComprobanteAfipId(Integer comprobanteAfipId);

}
