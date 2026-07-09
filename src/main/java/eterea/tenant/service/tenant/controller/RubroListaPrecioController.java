package eterea.tenant.service.tenant.controller;

import eterea.tenant.service.tenant.kotlin.exception.RubroListaPrecioException;
import eterea.tenant.service.tenant.kotlin.model.RubroListaPrecio;
import eterea.tenant.service.tenant.service.RubroListaPrecioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/tenant/core/rubroListaPrecio")
@RequiredArgsConstructor
public class RubroListaPrecioController {

    private final RubroListaPrecioService service;

    @GetMapping("/")
    public ResponseEntity<List<RubroListaPrecio>> findAll() {
        return ResponseEntity.ok(service.findAllByPublicar());
    }

    @GetMapping("/{rubroId}")
    public ResponseEntity<RubroListaPrecio> findByRubroId(@PathVariable Long rubroId) {
        try {
            return ResponseEntity.ok(service.findByRubroId(rubroId));
        } catch (RubroListaPrecioException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<RubroListaPrecio> addOrUpdate(@RequestBody RubroListaPrecio rubroListaPrecio) {
        return ResponseEntity.ok(service.addOrUpdate(rubroListaPrecio));
    }

}
