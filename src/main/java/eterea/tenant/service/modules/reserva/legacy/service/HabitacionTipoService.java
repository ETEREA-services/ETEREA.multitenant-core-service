/**
 * 
 */
package eterea.tenant.service.modules.reserva.legacy.service;

import java.util.List;

import eterea.tenant.service.modules.reserva.legacy.HabitacionTipo;
import eterea.tenant.service.modules.reserva.legacy.repository.HabitacionTipoRepository;
import org.springframework.stereotype.Service;

/**
 * @author daniel
 *
 */
@Service
public class HabitacionTipoService {

	private final HabitacionTipoRepository repository;

	public HabitacionTipoService(HabitacionTipoRepository repository) {
		this.repository = repository;
	}

	public List<HabitacionTipo> findAll() {
		return repository.findAll();
	}

}
