/**
 * 
 */
package eterea.tenant.service.modules.reserva.legacy.service;

import java.math.BigDecimal;
import java.util.List;

import eterea.tenant.service.modules.reserva.legacy.TarifaHabitacion;
import eterea.tenant.service.modules.reserva.legacy.repository.TarifaHabitacionRepository;
import org.springframework.stereotype.Service;

/**
 * @author daniel
 *
 */
@Service
public class TarifaHabitacionService {

	private final TarifaHabitacionRepository repository;

	public TarifaHabitacionService(TarifaHabitacionRepository repository) {
		this.repository = repository;
	}

	public List<TarifaHabitacion> findAllSinBloqueo(Boolean bloqueoEspecial) {
		if (!bloqueoEspecial) {
			return repository.findAllByBloqueoFacturaAndPrecioOrderByDescripcion((byte) 0, BigDecimal.ZERO);
		}
		return repository.findAllByBloqueoFacturaOrderByDescripcion((byte) 0);
	}

}
