/**
 * 
 */
package eterea.tenant.service.modules.personal.legacy.service;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import eterea.tenant.service.modules.personal.legacy.exception.LegajoRegistroException;
import eterea.tenant.service.modules.personal.legacy.LegajoRegistro;
import eterea.tenant.service.modules.personal.legacy.repository.LegajoRegistroRepository;
import org.springframework.stereotype.Service;

/**
 * @author daniel
 *
 */
@Service
public class LegajoRegistroService {

	private final LegajoRegistroRepository repository;

	public LegajoRegistroService(LegajoRegistroRepository repository) {
		this.repository = repository;
	}

	public List<LegajoRegistro> findAllByLegajoId(Integer legajoId) {
		return repository.findAllByLegajoId(legajoId);
	}

	public LegajoRegistro findLastByLegajoId(Integer legajoId) {
		return repository.findLastByLegajoId(legajoId).orElseThrow(() -> new LegajoRegistroException(legajoId));
	}

	public LegajoRegistro findLastByLegajoIdAndFecha(Integer legajoId, Date fecha, Time hora) {
		return repository.findLastByLegajoIdAndFecha(legajoId, fecha, hora).orElseThrow(() -> new LegajoRegistroException(legajoId));
	}
}
