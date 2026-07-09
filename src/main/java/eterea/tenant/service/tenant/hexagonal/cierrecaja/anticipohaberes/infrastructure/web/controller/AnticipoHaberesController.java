package eterea.tenant.service.tenant.hexagonal.cierrecaja.anticipohaberes.infrastructure.web.controller;

import eterea.tenant.service.tenant.hexagonal.cierrecaja.anticipohaberes.application.service.CierreCajaAnticipoHaberesService;
import eterea.tenant.service.tenant.hexagonal.cierrecaja.anticipohaberes.domain.model.CierreCajaAnticipoHaberes;
import eterea.tenant.service.tenant.hexagonal.cierrecaja.anticipohaberes.infrastructure.web.dto.AnticipoHaberesRequest;
import eterea.tenant.service.tenant.hexagonal.cierrecaja.anticipohaberes.infrastructure.web.dto.AnticipoHaberesResponse;
import eterea.tenant.service.tenant.hexagonal.cierrecaja.anticipohaberes.infrastructure.web.mapper.AnticipoHaberesDtoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenant/core/cierrecaja/anticipohaberes")
public class AnticipoHaberesController {

    private final CierreCajaAnticipoHaberesService service;
    private final AnticipoHaberesDtoMapper mapper;

    public AnticipoHaberesController(CierreCajaAnticipoHaberesService service, AnticipoHaberesDtoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping("/register/{cierreCajaId}/user/{userId}")
    public ResponseEntity<List<AnticipoHaberesResponse>> registerAnticipoHaberes(@RequestBody List<AnticipoHaberesRequest> anticipos, @PathVariable Long cierreCajaId, @PathVariable Long userId) {
        List<CierreCajaAnticipoHaberes> anticiposSaved = service.saveAllAnticipoHaberes(
                cierreCajaId,
                userId,
                anticipos.stream()
                        .map(mapper::toData)
                        .toList());
        return ResponseEntity.ok(anticiposSaved.stream()
                .map(mapper::toResponse)
                .toList());
    }

}
