package eterea.tenant.service.tenant.controller.facade;

import eterea.tenant.service.tenant.model.ClienteMovimiento;
import eterea.tenant.service.tenant.model.dto.DatoUnaFacturaDto;
import eterea.tenant.service.tenant.service.facade.FacturacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tenant/core/facturacion")
@RequiredArgsConstructor
public class FacturacionController {

    private final FacturacionService service;

    @PostMapping("/registrarTransaccionFacturaFaltante")
    public ResponseEntity<ClienteMovimiento> registrarTransaccionFacturaFaltante(@RequestBody DatoUnaFacturaDto datoUnaFactura) {
        return ResponseEntity.ok(service.registraTransaccionFacturaFaltante(datoUnaFactura.getClienteMovimiento(), datoUnaFactura.getArticuloMovimiento()));
    }

    @GetMapping("/registro/from/factura/{clienteMovimientoId}/nota/credito/{comprobanteId}")
    public ResponseEntity<ClienteMovimiento> registroNotaCreditoFromFactura(
            @PathVariable Long clienteMovimientoId,
            @PathVariable Integer comprobanteId
    ) {
        return ResponseEntity.ok(service.registroNotaCreditoFromFactura(clienteMovimientoId, comprobanteId));
    }

}
