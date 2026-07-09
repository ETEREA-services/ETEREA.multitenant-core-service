package eterea.tenant.service.tenant.controller.facade;

import eterea.tenant.service.tenant.hexagonal.facturacion.arca.nacional.infrastructure.web.dto.FacturacionDto;
import eterea.tenant.service.tenant.service.facade.TransaccionFacturaProgramaDiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tenant/core/transaccion-factura-programa-dia")
@RequiredArgsConstructor
public class TransaccionFacturaProgramaDiaController {

    private final TransaccionFacturaProgramaDiaService service;

    @PostMapping("/registro/{orderNumberId}/solo-factura/{soloFactura}/dry-run/{dryRun}")
    public ResponseEntity<Void> registroTransaccionFacturaProgramaDia(@PathVariable Long orderNumberId, @PathVariable Boolean soloFactura, @PathVariable Boolean dryRun, @RequestBody FacturacionDto facturacionDto) {
        service.registroTransaccionFacturaProgramaDia(orderNumberId, facturacionDto, soloFactura, dryRun);
        return ResponseEntity.ok().build();
    }
}
