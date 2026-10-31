/**
 * 
 */
package eterea.tenant.service.modules.platform.legacy.controller.view;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eterea.tenant.service.modules.platform.legacy.model.view.UsuarioVencimiento;
import eterea.tenant.service.modules.platform.legacy.service.view.UsuarioVencimientoService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/tenant/core/usuarioVencimiento")
@RequiredArgsConstructor
public class UsuarioVencimientoController {

	private final UsuarioVencimientoService service;

	@GetMapping("/today")
	public ResponseEntity<List<UsuarioVencimiento>> findAllToday() {
		return new ResponseEntity<>(service.findAllToday(), HttpStatus.OK);
	}

}
