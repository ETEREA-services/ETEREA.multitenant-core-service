package eterea.tenant.service.tenant.hexagonal.facturacion.arca.exportacion.infrastructure.web.controller;

import eterea.tenant.service.tenant.hexagonal.facturacion.arca.exportacion.application.service.FacturaElectronicaExportacionService;
import eterea.tenant.service.tenant.hexagonal.facturacion.arca.exportacion.infrastructure.web.dto.FacturaExportacionContextRequest;
import eterea.tenant.service.tenant.hexagonal.facturacion.arca.exportacion.infrastructure.web.dto.FacturaExportacionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tenant/core/makeFacturaExportacion")
@RequiredArgsConstructor
public class MakeFacturaExportacionController {

    private final FacturaElectronicaExportacionService facturaElectronicaExportacionService;

    @PostMapping("/arca/make/exportacion")
    public ResponseEntity<FacturaExportacionResponse> makeFacturaExportacion(@RequestBody FacturaExportacionContextRequest facturaExportacionContextRequest) {
        return ResponseEntity.ok(facturaElectronicaExportacionService.makeFactura(facturaExportacionContextRequest));
    }

}
