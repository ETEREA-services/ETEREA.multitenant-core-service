package eterea.tenant.service.tenant.controller;

import eterea.tenant.service.tenant.kotlin.exception.RegistroCaeException;
import eterea.tenant.service.tenant.model.RegistroCae;
import eterea.tenant.service.tenant.service.RegistroCaeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/tenant/core/registroCae")
@RequiredArgsConstructor
public class RegistroCaeController {

    private final RegistroCaeService service;

    @GetMapping("/unique/{comprobanteId}/{puntoVenta}/{numeroComprobante}")
    public ResponseEntity<RegistroCae> findByUnique(@PathVariable Integer comprobanteId, @PathVariable Integer puntoVenta, @PathVariable Long numeroComprobante) {
        try {
            return new ResponseEntity<>(service.findByUnique(comprobanteId, puntoVenta, numeroComprobante), HttpStatus.OK);
        } catch (RegistroCaeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<RegistroCae> create(@RequestBody RegistroCae registroCae) {
        return new ResponseEntity<>(service.add(registroCae), HttpStatus.CREATED);
    }

}
