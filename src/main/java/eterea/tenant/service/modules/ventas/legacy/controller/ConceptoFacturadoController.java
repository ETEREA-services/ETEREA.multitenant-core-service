package eterea.tenant.service.modules.ventas.legacy.controller;

import eterea.tenant.service.modules.ventas.legacy.ConceptoFacturadoException;
import eterea.tenant.service.modules.ventas.legacy.ConceptoFacturado;
import eterea.tenant.service.modules.ventas.legacy.service.ConceptoFacturadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/tenant/core/conceptoFacturado")
@RequiredArgsConstructor
public class ConceptoFacturadoController {

    private final ConceptoFacturadoService service;

    @GetMapping("/articuloMovimiento/{articuloMovimientoId}")
    public ResponseEntity<ConceptoFacturado> findByArticuloMovimientoId(@PathVariable Long articuloMovimientoId) {
        try {
            return ResponseEntity.ok(service.findByArticuloMovimientoId(articuloMovimientoId));
        } catch (ConceptoFacturadoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
