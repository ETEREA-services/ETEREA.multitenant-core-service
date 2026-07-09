package eterea.tenant.service.tenant.controller;

import eterea.tenant.service.tenant.model.ReservaContext;
import eterea.tenant.service.tenant.service.ReservaContextService;
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
