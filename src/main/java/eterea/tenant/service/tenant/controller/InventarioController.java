package eterea.tenant.service.tenant.controller;

import eterea.tenant.service.tenant.kotlin.model.Inventario;
import eterea.tenant.service.tenant.service.InventarioService;
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
