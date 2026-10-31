/**
 * 
 */
package eterea.tenant.service.modules.reserva.legacy.controller;

import java.util.List;

import eterea.tenant.service.modules.reserva.legacy.HabitacionTipo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eterea.tenant.service.modules.reserva.legacy.service.HabitacionTipoService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/tenant/core/habitaciontipo")
@RequiredArgsConstructor
public class HabitacionTipoController {

	private final HabitacionTipoService service;

	@GetMapping("/")
	public ResponseEntity<List<HabitacionTipo>> findAll() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}

}
