package eterea.tenant.service.modules.reserva.legacy.controller;

import eterea.tenant.service.modules.reserva.legacy.ReservaOrigen;
import eterea.tenant.service.modules.reserva.legacy.service.ReservaOrigenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tenant/core/reservaorigen")
@RequiredArgsConstructor
public class ReservaOrigenController {

    private final ReservaOrigenService service;

    @GetMapping("/")
    public ResponseEntity<List<ReservaOrigen>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{reservaOrigenId}")
    public ResponseEntity<ReservaOrigen> findByReservaOrigenId(@PathVariable Integer reservaOrigenId) {
        return new ResponseEntity<>(service.findByReservaOrigenId(reservaOrigenId), HttpStatus.OK);
    }

}
