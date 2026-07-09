package eterea.tenant.service.tenant.hexagonal.cuenta.infrastructure.web.controller;

import eterea.tenant.service.tenant.hexagonal.cuenta.application.service.CuentaService;
import eterea.tenant.service.tenant.hexagonal.cuenta.domain.model.Cuenta;
import eterea.tenant.service.tenant.hexagonal.cuenta.infrastructure.web.dto.CuentaResponse;
import eterea.tenant.service.tenant.hexagonal.cuenta.infrastructure.web.mapper.CuentaDtoMapper;
import eterea.tenant.service.tenant.kotlin.exception.CuentaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/tenant/core/cuenta")
public class CuentaController {

    private final CuentaService service;
    private final CuentaDtoMapper mapper;

    public CuentaController(CuentaService service, CuentaDtoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/{numeroCuenta}")
    public ResponseEntity<CuentaResponse> findByNumeroCuenta(@PathVariable Long numeroCuenta) {
        try {
            Cuenta domain = service.findByNumeroCuenta(numeroCuenta);
            return ResponseEntity.ok(mapper.toResponse(domain));
        } catch (CuentaException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
