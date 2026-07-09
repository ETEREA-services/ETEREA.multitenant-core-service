/**
 * 
 */
package eterea.tenant.service.tenant.controller.view;

import java.time.OffsetDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eterea.tenant.service.tenant.model.view.HabitacionMovimientoExtended;
import eterea.tenant.service.tenant.service.view.HabitacionMovimientoExtendedService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/tenant/core/habitacionmovimientoextended")
@RequiredArgsConstructor
public class HabitacionMovimientoExtendedController {

	private final HabitacionMovimientoExtendedService service;

	@GetMapping("/periodo/{desde}/{hasta}")
	public ResponseEntity<List<HabitacionMovimientoExtended>> findAllByPeriodo(
			@PathVariable @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime desde,
			@PathVariable @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime hasta) {
		return new ResponseEntity<>(service.findAllByPeriodo(desde, hasta),
				HttpStatus.OK);
	}

	@GetMapping("/reserva/{numeroReserva}")
	public ResponseEntity<HabitacionMovimientoExtended> findByNumeroReserva(@PathVariable Long numeroReserva) {
		return new ResponseEntity<>(service.findByNumeroReserva(numeroReserva),
				HttpStatus.OK);
	}

}
