/**
 * 
 */
package eterea.tenant.service.modules.platform.legacy.service;

import eterea.tenant.service.modules.platform.legacy.exception.MonedaException;
import eterea.tenant.service.modules.platform.legacy.Moneda;
import eterea.tenant.service.modules.platform.legacy.repository.MonedaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author daniel
 *
 */
@Service
public class MonedaService {

	private final MonedaRepository repository;

	public MonedaService(MonedaRepository repository) {
		this.repository = repository;
	}

	public List<Moneda> findAll() {
		return repository.findAll();
	}

	public Moneda findByMonedaId(Integer monedaId) {
		return repository.findByMonedaId(monedaId).orElseThrow(() -> new MonedaException(monedaId));
	}

}
