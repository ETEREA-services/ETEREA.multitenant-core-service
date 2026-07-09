package eterea.tenant.service.tenant.controller;

import eterea.tenant.service.tenant.kotlin.model.CentroStock;
import eterea.tenant.service.tenant.service.CentroStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tenant/core/centrostock")
public class CentroStockController {

    private final CentroStockService service;

    public CentroStockController(CentroStockService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<List<CentroStock>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

}
