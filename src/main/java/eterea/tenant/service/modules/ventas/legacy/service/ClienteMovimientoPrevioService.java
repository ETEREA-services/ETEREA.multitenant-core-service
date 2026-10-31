/**
 * 
 */
package eterea.tenant.service.modules.ventas.legacy.service;

import eterea.tenant.service.modules.platform.legacy.exception.ClienteMovimientoPrevioException;
import eterea.tenant.service.modules.ventas.legacy.ClienteMovimientoPrevio;
import eterea.tenant.service.modules.ventas.legacy.repository.ClienteMovimientoPrevioRepository;
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
