/**
 * 
 */
package eterea.tenant.service.tenant.controller;

import eterea.tenant.service.tenant.kotlin.model.Habitacion;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eterea.tenant.service.tenant.service.HabitacionService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/tenant/core/habitacion")
@RequiredArgsConstructor
public class HabitacionController {

	private final HabitacionService service;

	@GetMapping("/{numero}")
	public ResponseEntity<Habitacion> findByNumero(@PathVariable Integer numero) {
		return new ResponseEntity<>(service.findByNumero(numero), HttpStatus.OK);
	}

}
