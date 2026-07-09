package eterea.tenant.service.tenant.hexagonal.empresa.infrastructure.web.controller;

import eterea.tenant.service.tenant.hexagonal.empresa.application.service.EmpresaService;
import eterea.tenant.service.tenant.hexagonal.empresa.infrastructure.web.dto.EmpresaResponse;
import eterea.tenant.service.tenant.hexagonal.empresa.infrastructure.web.mapper.EmpresaDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tenant/core/empresa")
@RequiredArgsConstructor
public class EmpresaController {

    private final EmpresaService empresaService;
    private final EmpresaDtoMapper empresaDtoMapper;

    @GetMapping("/top")
    public ResponseEntity<EmpresaResponse> findTop() {
        return empresaService
                .findLast()
                .map(empresa -> ResponseEntity.ok(empresaDtoMapper.toResponse(empresa)))
                .orElse(ResponseEntity.notFound().build());
    }

}