/**
 * 
 */
package eterea.tenant.service.tenant.controller.view;

import java.time.OffsetDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eterea.tenant.service.tenant.model.view.ProductoCantidad;
import eterea.tenant.service.tenant.service.view.ProductoCantidadService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/tenant/core/productocantidad")
@RequiredArgsConstructor
public class ProductoCantidadController {

	private final ProductoCantidadService service;

	@GetMapping("/fechaServicio/{fechaServicio}")
	public ResponseEntity<List<ProductoCantidad>> findAllByFechaServicio(
			@PathVariable @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime fechaServicio) {
        return ResponseEntity.ok(service.findAllByFechaServicio(fechaServicio));
	}
}
