/**
 * 
 */
package eterea.tenant.service.tenant.service;

import java.util.List;

import eterea.tenant.service.tenant.exception.ReservaOrigenException;
import eterea.tenant.service.tenant.kotlin.model.ReservaOrigen;
import eterea.tenant.service.tenant.repository.ReservaOrigenRepository;
import org.springframework.stereotype.Service;

/**
 * @author daniel
 *
 */
@Service
public class ReservaOrigenService {

	private final ReservaOrigenRepository repository;

	public ReservaOrigenService(ReservaOrigenRepository repository) {
		this.repository = repository;
	}

	public List<ReservaOrigen> findAll() {
		return repository.findAll();
	}

	public ReservaOrigen findByReservaOrigenId(Integer reservaOrigenId) {
		return repository.findByReservaOrigenId(reservaOrigenId)
				.orElseThrow(() -> new ReservaOrigenException(reservaOrigenId));
	}

}
