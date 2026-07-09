package eterea.tenant.service.tenant.controller;

import eterea.tenant.service.tenant.kotlin.model.ComprobanteAfip;
import eterea.tenant.service.tenant.service.ComprobanteAfipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tenant/core/comprobanteAfip")
public class ComprobanteAfipController {

    private final ComprobanteAfipService service;

    public ComprobanteAfipController(ComprobanteAfipService service) {
        this.service = service;
    }

    @GetMapping("/{comprobanteAfipId}")
    public ResponseEntity<ComprobanteAfip> findByComprobanteAfipId(@PathVariable Integer comprobanteAfipId) {
        return ResponseEntity.ok(service.findByComprobanteAfipId(comprobanteAfipId));
    }

}
