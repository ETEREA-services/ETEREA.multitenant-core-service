/**
 * 
 */
package eterea.tenant.service.tenant.controller;

import java.util.List;

import eterea.tenant.service.tenant.kotlin.model.TarifaHabitacion;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eterea.tenant.service.tenant.service.TarifaHabitacionService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/tenant/core/tarifahabitacion")
@RequiredArgsConstructor
public class TarifaHabitacionController {

	private final TarifaHabitacionService service;

	@GetMapping("/combo/{bloqueoEspecial}")
	public ResponseEntity<List<TarifaHabitacion>> findAllSinBloqueo(@PathVariable Boolean bloqueoEspecial) {
		return new ResponseEntity<>(service.findAllSinBloqueo(bloqueoEspecial), HttpStatus.OK);
	}
}
