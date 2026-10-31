/**
 * 
 */
package eterea.tenant.service.modules.platform.legacy.repository.view;

import java.util.List;

import eterea.tenant.service.modules.platform.legacy.ClienteSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author daniel
 *
 */
@Repository
public interface IClienteSearchRepository extends JpaRepository<ClienteSearch, Long> {

	public List<ClienteSearch> findTop50BySearchLikeOrderBySearchAsc(String search);

}
