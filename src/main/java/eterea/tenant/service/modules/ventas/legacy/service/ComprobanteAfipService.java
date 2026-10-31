/**
 * 
 */
package eterea.tenant.service.modules.ventas.legacy.service;

import eterea.tenant.service.modules.ventas.legacy.exception.ComprobanteAfipException;
import eterea.tenant.service.modules.ventas.legacy.ComprobanteAfip;
import eterea.tenant.service.modules.ventas.legacy.repository.ComprobanteAfipRepository;
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
