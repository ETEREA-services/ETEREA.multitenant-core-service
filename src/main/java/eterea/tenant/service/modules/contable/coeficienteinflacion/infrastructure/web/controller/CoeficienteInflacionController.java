package eterea.tenant.service.modules.contable.coeficienteinflacion.infrastructure.web.controller;

import eterea.tenant.service.modules.contable.coeficienteinflacion.application.service.CoeficienteInflacionService;
import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.model.CoeficienteInflacionAnio;
import eterea.tenant.service.modules.contable.coeficienteinflacion.infrastructure.web.dto.CoeficienteInflacionAnioResponse;
import eterea.tenant.service.modules.contable.coeficienteinflacion.infrastructure.web.dto.CoeficienteInflacionAnioUpdateRequest;
import eterea.tenant.service.modules.contable.coeficienteinflacion.infrastructure.web.mapper.CoeficienteInflacionDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tenant/core/coeficiente-inflacion")
@RequiredArgsConstructor
public class CoeficienteInflacionController {

    private final CoeficienteInflacionService service;
    private final CoeficienteInflacionDtoMapper mapper;

    @GetMapping("/anios")
    public ResponseEntity<List<Integer>> findAnios() {
        return ResponseEntity.ok(service.findAnios());
    }

    @GetMapping("/anio/{anho}")
    public ResponseEntity<CoeficienteInflacionAnioResponse> findAnio(
            @PathVariable Integer anho) {
        return ResponseEntity.ok(mapper.toResponse(service.findAnio(anho)));
    }

    @PutMapping("/anio/{anho}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CoeficienteInflacionAnioResponse> save(
            @PathVariable Integer anho,
            @Valid @RequestBody CoeficienteInflacionAnioUpdateRequest request) {
        CoeficienteInflacionAnio actualizado =
                service.save(anho, mapper.toCambios(request));
        return ResponseEntity.ok(mapper.toResponse(actualizado));
    }

}
