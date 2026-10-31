/**
 * 
 */
package eterea.tenant.service.modules.platform.legacy.repository;

import eterea.tenant.service.modules.platform.legacy.ClienteInternet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author daniel
 *
 */
@Repository
public interface ClienteInternetRepository extends JpaRepository<ClienteInternet, Long> {

}
