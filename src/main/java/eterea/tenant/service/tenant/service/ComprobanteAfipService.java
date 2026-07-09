/**
 * 
 */
package eterea.tenant.service.tenant.service;

import eterea.tenant.service.tenant.exception.ComprobanteAfipException;
import eterea.tenant.service.tenant.kotlin.model.ComprobanteAfip;
import eterea.tenant.service.tenant.repository.ComprobanteAfipRepository;
import org.springframework.stereotype.Service;

/**
 * @author daniel
 *
 */
@Service
public class ComprobanteAfipService {

	private final ComprobanteAfipRepository repository;

	public ComprobanteAfipService(ComprobanteAfipRepository repository) {
		this.repository = repository;
	}

	public ComprobanteAfip findByComprobanteAfipId(Integer comprobanteAfipId) {
		return repository.findByComprobanteAfipId(comprobanteAfipId)
				.orElseThrow(() -> new ComprobanteAfipException(comprobanteAfipId));
	}

}
