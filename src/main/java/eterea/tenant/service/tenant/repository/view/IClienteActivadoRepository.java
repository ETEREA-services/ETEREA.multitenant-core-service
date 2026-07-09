/**
 * 
 */
package eterea.tenant.service.tenant.repository.view;

import eterea.tenant.service.tenant.kotlin.model.view.ClienteActivado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author daniel
 *
 */
@Repository
public interface IClienteActivadoRepository extends JpaRepository<ClienteActivado, Long> {

}
