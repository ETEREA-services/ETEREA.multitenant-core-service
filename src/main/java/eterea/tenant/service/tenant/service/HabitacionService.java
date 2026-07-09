/**
 * 
 */
package eterea.tenant.service.tenant.service;

import eterea.tenant.service.tenant.exception.HabitacionException;
import eterea.tenant.service.tenant.kotlin.model.Habitacion;
import eterea.tenant.service.tenant.repository.HabitacionRepository;
import org.springframework.stereotype.Service;

/**
 * @author daniel
 *
 */
@Service
public class HabitacionService {

	private final HabitacionRepository repository;

	public HabitacionService(HabitacionRepository repository) {
		this.repository = repository;
	}

	public Habitacion findByNumero(Integer numero) {
		return repository.findByNumero(numero).orElseThrow(() -> new HabitacionException(numero));
	}

	public Habitacion update(Habitacion newHabitacion, Integer numero) {
		Habitacion habitacion = repository.findByNumero(numero)
				.orElseThrow(() -> new HabitacionException(numero));

		habitacion.setHabitacionTipoId(newHabitacion.getHabitacionTipoId());
		habitacion.setClienteId(newHabitacion.getClienteId());

		return repository.save(habitacion);
	}

}
