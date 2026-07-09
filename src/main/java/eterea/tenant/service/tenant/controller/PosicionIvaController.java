package eterea.tenant.service.tenant.controller;

import eterea.tenant.service.tenant.exception.PosicionIvaException;
import eterea.tenant.service.tenant.model.PosicionIva;
import eterea.tenant.service.tenant.service.PosicionIvaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/tenant/core/posicionIva")
@RequiredArgsConstructor
public class PosicionIvaController {

    private final PosicionIvaService service;

    @GetMapping("/{posicionId}")
    public ResponseEntity<PosicionIva> findByPosicionId(@PathVariable Integer posicionId) {
        try {
            return ResponseEntity.ok(service.findByPosicionId(posicionId));
        } catch (PosicionIvaException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
