package eterea.tenant.service.tenant.controller;

import eterea.tenant.service.tenant.kotlin.model.Moneda;
import eterea.tenant.service.tenant.service.MonedaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tenant/core/moneda")
@RequiredArgsConstructor
public class MonedaController {

    private final MonedaService service;

    @GetMapping("/")
    public ResponseEntity<List<Moneda>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{monedaId}")
    public ResponseEntity<Moneda> findByMonedaId(@PathVariable Integer monedaId) {
        return ResponseEntity.ok(service.findByMonedaId(monedaId));
    }

}
