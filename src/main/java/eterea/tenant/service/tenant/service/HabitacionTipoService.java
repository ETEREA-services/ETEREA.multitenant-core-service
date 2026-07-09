/**
 * 
 */
package eterea.tenant.service.tenant.service;

import java.util.List;

import eterea.tenant.service.tenant.kotlin.model.HabitacionTipo;
import eterea.tenant.service.tenant.repository.HabitacionTipoRepository;
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
