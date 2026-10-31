package eterea.tenant.service.modules.reserva.legacy.controller;

import eterea.tenant.service.modules.reserva.legacy.ReservaContext;
import eterea.tenant.service.modules.reserva.legacy.service.ReservaContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tenant/core/reservacontext")
@RequiredArgsConstructor
public class ReservaContextController {

    private final ReservaContextService service;

    @GetMapping("/facturacion/pendiente")
    public ResponseEntity<List<ReservaContext>> findAllByFacturacionPendiente() {
        return ResponseEntity.ok(service.findAllByFacturacionPendiente());
    }

}
