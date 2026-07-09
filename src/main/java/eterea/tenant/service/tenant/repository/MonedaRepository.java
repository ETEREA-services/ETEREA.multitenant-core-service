/**
 * 
 */
package eterea.tenant.service.tenant.repository;

import java.util.Optional;

import eterea.tenant.service.tenant.kotlin.model.Moneda;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author daniel
 *
 */
public interface MonedaRepository extends JpaRepository<Moneda, Integer> {

	Optional<Moneda> findByMonedaId(Integer monedaId);

}
