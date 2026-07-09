/**
 * 
 */
package eterea.tenant.service.tenant.controller;

import eterea.tenant.service.tenant.kotlin.model.Proveedor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import eterea.tenant.service.tenant.exception.ProveedorException;
import eterea.tenant.service.tenant.service.ProveedorService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/tenant/core/proveedor")
@RequiredArgsConstructor
public class ProveedorController {

	private final ProveedorService service;

	@GetMapping("/{proveedorId}")
	public ResponseEntity<Proveedor> findByProveedorId(@PathVariable Integer proveedorId) {
		try {
			return new ResponseEntity<>(service.findByProveedorId(proveedorId), HttpStatus.OK);
		} catch (ProveedorException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
