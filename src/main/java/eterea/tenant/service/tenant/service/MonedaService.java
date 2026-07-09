/**
 * 
 */
package eterea.tenant.service.tenant.service;

import eterea.tenant.service.tenant.exception.MonedaException;
import eterea.tenant.service.tenant.kotlin.model.Moneda;
import eterea.tenant.service.tenant.repository.MonedaRepository;
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
