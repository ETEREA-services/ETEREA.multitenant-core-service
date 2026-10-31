/**
 * 
 */
package eterea.tenant.service.modules.platform.legacy.repository;

import java.util.Optional;

import eterea.tenant.service.modules.platform.legacy.Moneda;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author daniel
 *
 */
public interface MonedaRepository extends JpaRepository<Moneda, Integer> {

	Optional<Moneda> findByMonedaId(Integer monedaId);

}
