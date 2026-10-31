package eterea.tenant.service.modules.contable.cuenta.infrastructure.web.controller;

import eterea.tenant.service.modules.contable.legacy.CuentaDto;
import eterea.tenant.service.modules.contable.cuenta.application.service.CuentaService;
import eterea.tenant.service.modules.contable.cuenta.domain.model.Cuenta;
import eterea.tenant.service.modules.contable.cuenta.infrastructure.web.dto.CuentaDetalleResponse;
import eterea.tenant.service.modules.contable.cuenta.infrastructure.web.dto.CuentaNodoResponse;
import eterea.tenant.service.modules.contable.cuenta.infrastructure.web.dto.CuentaRequest;
import eterea.tenant.service.modules.contable.cuenta.infrastructure.web.dto.CuentaResponse;
import eterea.tenant.service.modules.contable.cuenta.infrastructure.web.dto.CuentaUpdateRequest;
import eterea.tenant.service.modules.contable.cuenta.infrastructure.web.mapper.CuentaDtoMapper;
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
@RequestMapping("/api/tenant/core/cuenta")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaService service;
    private final CuentaDtoMapper mapper;

    @GetMapping
    public ResponseEntity<List<CuentaDetalleResponse>> findAll() {
        return ResponseEntity.ok(service.findAllOrdered().stream()
                .map(mapper::toDetalleResponse)
                .toList());
    }

    @GetMapping("/arbol")
    public ResponseEntity<List<CuentaNodoResponse>> findArbol() {
        return ResponseEntity.ok(service.findArbol().stream()
                .map(mapper::toNodoResponse)
                .toList());
    }

    /**
     * Endpoint de compatibilidad: mantiene el contrato con {@code CuentaDto}
     * (flags {@code Byte}) que consumen comprobantes y otros servicios.
     */
    @GetMapping("/{numeroCuenta}")
    public ResponseEntity<CuentaResponse> findByNumeroCuenta(@PathVariable Long numeroCuenta) {
        return ResponseEntity.ok(mapper.toResponse(service.findByNumeroCuenta(numeroCuenta)));
    }

    @GetMapping("/{numeroCuenta}/detalle")
    public ResponseEntity<CuentaDetalleResponse> findDetalle(@PathVariable Long numeroCuenta) {
        return ResponseEntity.ok(
                mapper.toDetalleResponse(service.findByNumeroCuenta(numeroCuenta)));
    }

    @PostMapping
    public ResponseEntity<CuentaDetalleResponse> create(
            @Valid @RequestBody CuentaRequest request) {
        Cuenta creada = service.create(mapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDetalleResponse(creada));
    }

    @PutMapping("/{numeroCuenta}")
    public ResponseEntity<CuentaDetalleResponse> update(
            @PathVariable Long numeroCuenta,
            @Valid @RequestBody CuentaUpdateRequest request) {
        Cuenta actualizada = service.update(numeroCuenta, mapper.toDomain(request));
        return ResponseEntity.ok(mapper.toDetalleResponse(actualizada));
    }

    @DeleteMapping("/{numeroCuenta}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long numeroCuenta) {
        service.delete(numeroCuenta);
        return ResponseEntity.noContent().build();
    }
}
