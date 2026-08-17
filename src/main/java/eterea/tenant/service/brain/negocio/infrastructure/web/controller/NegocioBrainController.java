package eterea.tenant.service.brain.negocio.infrastructure.web.controller;

import eterea.tenant.service.brain.negocio.application.exception.NegocioBrainException;
import eterea.tenant.service.brain.negocio.application.service.NegocioBrainService;
import eterea.tenant.service.brain.negocio.infrastructure.web.dto.NegocioBrainRequest;
import eterea.tenant.service.brain.negocio.infrastructure.web.dto.NegocioBrainResponse;
import eterea.tenant.service.brain.negocio.infrastructure.web.mapper.NegocioBrainDtoMapper;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/brain/core/negocio")
@RequiredArgsConstructor
public class NegocioBrainController {

    private final NegocioBrainService negocioBrainService;
    private final NegocioBrainDtoMapper negocioBrainDtoMapper;

    @GetMapping
    public ResponseEntity<List<NegocioBrainResponse>> findAll() {
        return ResponseEntity.ok(negocioBrainService.findAll().stream()
                .map(negocioBrainDtoMapper::toResponse)
                .toList());
    }

    @GetMapping("/{negocioId}")
    public ResponseEntity<NegocioBrainResponse> findById(@PathVariable Integer negocioId) {
        try {
            return ResponseEntity.ok(negocioBrainDtoMapper.toResponse(
                    negocioBrainService.findById(negocioId)));
        } catch (NegocioBrainException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<NegocioBrainResponse> findByTenantId(@PathVariable String tenantId) {
        try {
            return ResponseEntity.ok(negocioBrainDtoMapper.toResponse(
                    negocioBrainService.findByTenantId(tenantId)));
        } catch (NegocioBrainException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
    }

    @PostMapping
    public ResponseEntity<NegocioBrainResponse> create(
            @Valid @RequestBody NegocioBrainRequest request) {
        var negocio = negocioBrainService.create(negocioBrainDtoMapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(negocioBrainDtoMapper.toResponse(negocio));
    }

    @PutMapping("/{negocioId}")
    public ResponseEntity<NegocioBrainResponse> update(
            @PathVariable Integer negocioId,
            @Valid @RequestBody NegocioBrainRequest request) {
        try {
            return ResponseEntity.ok(negocioBrainDtoMapper.toResponse(
                    negocioBrainService.update(negocioId, negocioBrainDtoMapper.toDomain(request))));
        } catch (NegocioBrainException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
    }
}
