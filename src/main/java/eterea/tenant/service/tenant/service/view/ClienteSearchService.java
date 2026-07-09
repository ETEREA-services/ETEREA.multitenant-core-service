/**
 * 
 */
package eterea.tenant.service.tenant.service.view;

import java.util.List;

import eterea.tenant.service.tenant.kotlin.model.view.ClienteSearch;
import eterea.tenant.service.tenant.repository.view.IClienteSearchRepository;
import org.springframework.stereotype.Service;

/**
 * @author daniel
 *
 */
@Service
public class ClienteSearchService {

	private final IClienteSearchRepository repository;

	public ClienteSearchService(IClienteSearchRepository repository) {
		this.repository = repository;
	}

	public List<ClienteSearch> findAllBySearch(String search) {
		return repository.findTop50BySearchLikeOrderBySearchAsc("%" + search + "%");
	}

}
