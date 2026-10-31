/**
 * 
 */
package eterea.tenant.service.modules.reserva.legacy.service;

import eterea.tenant.service.modules.reserva.legacy.exception.HabitacionException;
import eterea.tenant.service.modules.reserva.legacy.Habitacion;
import eterea.tenant.service.modules.reserva.legacy.repository.HabitacionRepository;
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
