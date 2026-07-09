package eterea.tenant.service.tenant.controller.facade;

import eterea.tenant.service.tenant.model.dto.TransferenciaWrapperDto;
import eterea.tenant.service.tenant.service.facade.TransferenciasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tenant/core/transferencias")
@RequiredArgsConstructor
public class TransferenciasController {

    private final TransferenciasService service;

    @GetMapping("/unique/{negocioIdDesde}/{negocioIdHasta}/{numeroTransferencia}")
    public ResponseEntity<TransferenciaWrapperDto> findByUnique(@PathVariable Integer negocioIdDesde, @PathVariable Integer negocioIdHasta, @PathVariable Long numeroTransferencia) {
        return ResponseEntity.ok(service.findByUnique(negocioIdDesde, negocioIdHasta, numeroTransferencia));
    }

    @PostMapping("/regenerate")
    public ResponseEntity<String> regenerate(@RequestBody TransferenciaWrapperDto transferenciaWrapper) {
        return ResponseEntity.ok(service.regenerate(transferenciaWrapper));
    }

}
