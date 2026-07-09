package eterea.tenant.service.tenant.controller.facade;

import eterea.tenant.service.tenant.kotlin.model.StockMovimiento;
import eterea.tenant.service.tenant.model.dto.StockAndArticulosDto;
import eterea.tenant.service.tenant.service.facade.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tenant/core/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService service;

    @PostMapping("/add/movimiento")
    public ResponseEntity<StockMovimiento> addMovimiento(@RequestBody StockAndArticulosDto stockAndArticulos) {
        return ResponseEntity.ok(service.addMovimiento(stockAndArticulos));
    }

}
