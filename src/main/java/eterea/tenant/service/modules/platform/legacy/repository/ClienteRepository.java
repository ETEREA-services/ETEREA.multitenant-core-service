/**
 * 
 */
package eterea.tenant.service.modules.platform.legacy.repository;

import java.util.List;
import java.util.Optional;

import eterea.tenant.service.modules.platform.legacy.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author daniel
 *
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	Optional<Cliente> findByClienteId(Long clienteId);

	List<Cliente> findAllByClienteIdIn(List<Long> clienteIds);

	Optional<Cliente> findTopByNumeroDocumento(String numeroDocumento);

	Optional<Cliente> findTopByOrderByClienteIdDesc();

}
