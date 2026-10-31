/**
 * 
 */
package eterea.tenant.service.modules.platform.legacy.controller;

import eterea.tenant.service.modules.platform.legacy.Permiso;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eterea.tenant.service.modules.platform.legacy.service.PermisoService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/tenant/core/permiso")
@RequiredArgsConstructor
public class PermisoController {

	private final PermisoService service;

	@GetMapping("/permiso/{usuario}/{opcion}")
	public ResponseEntity<Permiso> findByPermiso(@PathVariable String usuario, @PathVariable String opcion) {
		return new ResponseEntity<>(service.findByPermiso(usuario, opcion), HttpStatus.OK);
	}

}
