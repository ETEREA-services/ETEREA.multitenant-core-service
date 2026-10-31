/**
 * 
 */
package eterea.tenant.service.modules.platform.legacy.service;

import java.util.List;

import eterea.tenant.service.modules.platform.legacy.exception.ClienteException;
import eterea.tenant.service.modules.platform.legacy.Cliente;
import eterea.tenant.service.modules.platform.legacy.ClienteSearch;
import eterea.tenant.service.modules.platform.legacy.repository.ClienteRepository;
import eterea.tenant.service.modules.platform.legacy.service.view.ClienteSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class ClienteService {

	private final ClienteRepository repository;
	private final ClienteSearchService clienteSearchService;

	public ClienteService(ClienteRepository repository, ClienteSearchService clienteSearchService) {
		this.repository = repository;
		this.clienteSearchService = clienteSearchService;
	}

	public List<ClienteSearch> findAllBySearch(String search) {
		return clienteSearchService.findAllBySearch(search);
	}

	public List<Cliente> findAllByIds(List<Long> clienteIds) {
		return repository.findAllByClienteIdIn(clienteIds);
	}

	public Cliente findByClienteId(Long clienteId) {
		return repository.findByClienteId(clienteId).orElseThrow(() -> new ClienteException(clienteId));
	}

	public Cliente findByNumeroDocumento(String numeroDocumento) {
		log.debug("Processing findByNumeroDocumento");
		return repository.findTopByNumeroDocumento(numeroDocumento)
				.orElseThrow(() -> new ClienteException(numeroDocumento));
	}

	public Cliente findLast() {
		return repository.findTopByOrderByClienteIdDesc().orElseThrow(ClienteException::new);
	}

	public Cliente add(Cliente cliente) {
		return repository.save(cliente);
	}

}
