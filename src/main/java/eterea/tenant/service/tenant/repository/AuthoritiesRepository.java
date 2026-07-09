/**
 * 
 */
package eterea.tenant.service.tenant.repository;

import java.util.List;

import eterea.tenant.service.tenant.kotlin.model.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author daniel
 *
 */
@Repository
public interface AuthoritiesRepository extends JpaRepository<Authorities, Long> {

	List<Authorities> findAllByClienteId(Long clienteId);

}
