package eterea.tenant.service.tenant.controller.facade;

import eterea.tenant.service.tenant.model.dto.ProgramaDiaDto;
import eterea.tenant.service.tenant.model.Track;
import eterea.tenant.service.tenant.service.TrackService;
import eterea.tenant.service.tenant.service.facade.VouchersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tenant/core/vouchers")
@RequiredArgsConstructor
public class VouchersController {

    private final VouchersService service;
    private final TrackService trackService;

    @GetMapping("/import/web/one/{orderNumberId}")
    public ResponseEntity<ProgramaDiaDto> importOneFromWeb(@PathVariable Long orderNumberId, @RequestHeader(name = "X-Request-ID", required = false) String trackUuid) {
        Track track;
        if (trackUuid != null) {
            track = trackService.findByUuid(trackUuid);
        } else {
            track = trackService.startTracking("import-one-from-web-" + orderNumberId);
        }
        return ResponseEntity.ok(service.importOneFromWeb(orderNumberId, track));
    }

}

