/**
 * 
 */
package eterea.tenant.service.modules.reserva.legacy.service.facade;

import eterea.tenant.service.modules.reserva.legacy.exception.HabitacionException;
import eterea.tenant.service.modules.reserva.legacy.exception.HabitacionTarifaException;
import eterea.tenant.service.modules.reserva.legacy.Habitacion;
import eterea.tenant.service.modules.reserva.legacy.HabitacionTarifa;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import eterea.tenant.service.modules.reserva.legacy.service.HabitacionService;
import eterea.tenant.service.modules.reserva.legacy.service.HabitacionTarifaService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class AlojamientoService {

	private final HabitacionService habitacionService;
	private final HabitacionTarifaService habitacionTarifaService;

	public AlojamientoService(HabitacionService habitacionService, HabitacionTarifaService habitacionTarifaService) {
		this.habitacionService = habitacionService;
		this.habitacionTarifaService = habitacionTarifaService;
	}

	@Transactional
	public Boolean saveHabitacion(Integer numero, Integer paxs, Integer habitacionTipoId, Boolean valorDefault) {
		if (valorDefault) {
			try {
				Habitacion habitacion = habitacionService.findByNumero(numero);
				habitacion.setHabitacionTipoId(habitacionTipoId);
				habitacion = habitacionService.update(habitacion, numero);
			} catch (HabitacionException e) {
				log.debug("Habitacion {} NO Encontrada", numero);
				return false;
			}
		}

		Long habitacionTarifaId = null;
		HabitacionTarifa habitacionTarifa = null;
		try {
			habitacionTarifa = habitacionTarifaService.findByUnique(numero, paxs);
			habitacionTarifaId = habitacionTarifa.getHabitacionTarifaId();
		} catch (HabitacionTarifaException e) {
			log.debug("Tarifa NO Existe");
		}
		habitacionTarifa = new HabitacionTarifa(habitacionTarifaId, numero, paxs, habitacionTipoId);
		if (habitacionTarifaId == null) {
			habitacionTarifa = habitacionTarifaService.add(habitacionTarifa);
		} else {
			habitacionTarifa = habitacionTarifaService.update(habitacionTarifa, habitacionTarifaId);
		}
		return true;
	}

}
