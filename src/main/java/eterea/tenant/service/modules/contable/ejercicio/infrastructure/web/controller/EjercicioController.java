package eterea.tenant.service.modules.contable.ejercicio.infrastructure.web.controller;

import eterea.tenant.service.modules.contable.ejercicio.application.service.EjercicioService;
import eterea.tenant.service.modules.contable.ejercicio.domain.model.Ejercicio;
import eterea.tenant.service.modules.contable.ejercicio.infrastructure.web.dto.BloqueoRequest;
import eterea.tenant.service.modules.contable.ejercicio.infrastructure.web.dto.EjercicioRequest;
import eterea.tenant.service.modules.contable.ejercicio.infrastructure.web.dto.EjercicioResponse;
import eterea.tenant.service.modules.contable.ejercicio.infrastructure.web.dto.EjercicioUpdateRequest;
import eterea.tenant.service.modules.contable.ejercicio.infrastructure.web.dto.SiguienteEjercicioResponse;
import eterea.tenant.service.modules.contable.ejercicio.infrastructure.web.mapper.EjercicioDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tenant/core/ejercicio")
@RequiredArgsConstructor
public class EjercicioController {

    private final EjercicioService service;
    private final EjercicioDtoMapper mapper;

    @GetMapping
    public ResponseEntity<List<EjercicioResponse>> findAll() {
        return ResponseEntity.ok(service.findAll().stream()
                .map(mapper::toResponse)
                .toList());
    }

    @GetMapping("/siguiente")
    public ResponseEntity<SiguienteEjercicioResponse> sugerirSiguiente() {
        return ResponseEntity.ok(mapper.toResponse(service.sugerirSiguiente()));
    }

    @GetMapping("/{ejercicioId}")
    public ResponseEntity<EjercicioResponse> findById(@PathVariable Long ejercicioId) {
        return ResponseEntity.ok(mapper.toResponse(service.findById(ejercicioId)));
    }

    @PostMapping
    public ResponseEntity<EjercicioResponse> create(
            @Valid @RequestBody EjercicioRequest request) {
        Ejercicio creado = service.create(mapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(creado));
    }

    @PutMapping("/{ejercicioId}")
    public ResponseEntity<EjercicioResponse> update(
            @PathVariable Long ejercicioId,
            @Valid @RequestBody EjercicioUpdateRequest request) {
        Ejercicio actualizado = service.update(ejercicioId, mapper.toDomain(request));
        return ResponseEntity.ok(mapper.toResponse(actualizado));
    }

    @PutMapping("/{ejercicioId}/bloqueo")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<EjercicioResponse> cambiarBloqueo(
            @PathVariable Long ejercicioId,
            @Valid @RequestBody BloqueoRequest request) {
        Ejercicio actualizado = service.cambiarBloqueo(
                ejercicioId, Boolean.TRUE.equals(request.getBloqueado()));
        return ResponseEntity.ok(mapper.toResponse(actualizado));
    }

    @DeleteMapping("/{ejercicioId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long ejercicioId) {
        service.delete(ejercicioId);
        return ResponseEntity.noContent().build();
    }

}
