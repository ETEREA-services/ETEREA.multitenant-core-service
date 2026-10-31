/**
 * 
 */
package eterea.tenant.service.modules.reserva.legacy.service;

import eterea.tenant.service.modules.reserva.legacy.exception.HabitacionTarifaException;
import eterea.tenant.service.modules.reserva.legacy.HabitacionTarifa;
import eterea.tenant.service.modules.reserva.legacy.repository.HabitacionTarifaRepository;
import org.springframework.stereotype.Service;

/**
 * @author daniel
 *
 */
@Service
public class HabitacionTarifaService {

	private final HabitacionTarifaRepository repository;

	public HabitacionTarifaService(HabitacionTarifaRepository repository) {
		this.repository = repository;
	}

	public HabitacionTarifa findByUnique(Integer numero, Integer paxs) {
		return repository.findByNumeroAndPaxs(numero, paxs)
				.orElseThrow(() -> new HabitacionTarifaException(numero, paxs));
	}

	public HabitacionTarifa add(HabitacionTarifa habitacionTarifa) {
		habitacionTarifa = repository.save(habitacionTarifa);
		return habitacionTarifa;
	}

	public HabitacionTarifa update(HabitacionTarifa habitacionTarifa, Long habitacionTarifaId) {
		return repository.findByHabitacionTarifaId(habitacionTarifaId)
				.orElseThrow(() -> new HabitacionTarifaException(habitacionTarifaId));
	}

}
