package eterea.tenant.service.modules.contable.moneda.infrastructure.web.controller;

import eterea.tenant.service.modules.contable.moneda.domain.ports.out.MonedaQueryPort;
import eterea.tenant.service.modules.contable.moneda.infrastructure.web.dto.MonedasResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Catálogo de monedas del tenant para los selectores de reportes.
 */
@RestController("contableMonedaController")
@RequestMapping("/api/tenant/core/moneda")
@RequiredArgsConstructor
public class MonedaController {

    private final MonedaQueryPort monedaQuery;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MonedasResponse> findAll() {
        return ResponseEntity.ok(new MonedasResponse(monedaQuery.findAll()));
    }

}
