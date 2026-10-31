/**
 * 
 */
package eterea.tenant.service.modules.platform.legacy.repository.view;

import eterea.tenant.service.modules.platform.legacy.ClienteActivado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author daniel
 *
 */
@Repository
public interface IClienteActivadoRepository extends JpaRepository<ClienteActivado, Long> {

}
