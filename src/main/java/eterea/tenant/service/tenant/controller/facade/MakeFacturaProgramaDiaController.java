package eterea.tenant.service.tenant.controller.facade;

import eterea.tenant.service.tenant.model.Track;
import eterea.tenant.service.tenant.service.TrackService;
import eterea.tenant.service.tenant.service.facade.MakeFacturaProgramaDiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tenant/core/make-factura-programa-dia")
@RequiredArgsConstructor
public class MakeFacturaProgramaDiaController {

    private final MakeFacturaProgramaDiaService service;
    private final TrackService trackService;

    @GetMapping("/factura/{reservaId}/{comprobanteId}")
    public ResponseEntity<Boolean> facturaReserva(@PathVariable Long reservaId,
                                                  @PathVariable Integer comprobanteId,
                                                  @RequestHeader(name = "X-Request-ID", required = false) String trackUuid) {
        Track track;
        if (trackUuid != null) {
            track = trackService.findByUuid(trackUuid);
        } else {
            track = trackService.startTracking("factura-reserva-" + reservaId);
        }
        return ResponseEntity.ok(service.facturaReserva(reservaId, comprobanteId, track));
    }

}
