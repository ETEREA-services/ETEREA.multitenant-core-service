/**
 * 
 */
package eterea.tenant.service.tenant.service;

import eterea.tenant.service.tenant.exception.ClienteMovimientoPrevioException;
import eterea.tenant.service.tenant.kotlin.model.ClienteMovimientoPrevio;
import eterea.tenant.service.tenant.repository.ClienteMovimientoPrevioRepository;
import org.springframework.stereotype.Service;

/**
 * @author daniel
 *
 */
@Service
public class ClienteMovimientoPrevioService {

	private final ClienteMovimientoPrevioRepository repository;

	public ClienteMovimientoPrevioService(ClienteMovimientoPrevioRepository repository) {
		this.repository = repository;
	}

	public ClienteMovimientoPrevio findByClienteMovimientoPrevioId(Long clienteMovimientoPrevioId) {
		return repository.findByClienteMovimientoPrevioId(clienteMovimientoPrevioId)
				.orElseThrow(() -> new ClienteMovimientoPrevioException(clienteMovimientoPrevioId));
	}

}
