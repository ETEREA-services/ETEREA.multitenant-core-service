package eterea.tenant.service.modules.stock.legacy.controller;

import eterea.tenant.service.modules.stock.legacy.Inventario;
import eterea.tenant.service.modules.stock.legacy.service.InventarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tenant/core/inventario")
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioService service;

    @PostMapping("/all")
    public ResponseEntity<List<Inventario>> addAll(@RequestBody List<Inventario> inventarios) {
        return new ResponseEntity<>(service.addAll(inventarios), HttpStatus.OK);
    }

}
