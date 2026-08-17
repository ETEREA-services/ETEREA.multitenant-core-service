package eterea.tenant.service.brain.empresa.infrastructure.web.controller;

import eterea.tenant.service.brain.empresa.application.exception.EmpresaBrainException;
import eterea.tenant.service.brain.empresa.application.service.EmpresaBrainService;
import eterea.tenant.service.brain.empresa.infrastructure.web.dto.EmpresaBrainRequest;
import eterea.tenant.service.brain.empresa.infrastructure.web.dto.EmpresaBrainResponse;
import eterea.tenant.service.brain.empresa.infrastructure.web.mapper.EmpresaBrainDtoMapper;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
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
@RequestMapping("/api/brain/core/empresa")
@RequiredArgsConstructor
public class EmpresaBrainController {

    private final EmpresaBrainService empresaBrainService;
    private final EmpresaBrainDtoMapper empresaBrainDtoMapper;

    @GetMapping
    public ResponseEntity<List<EmpresaBrainResponse>> findAll() {
        return ResponseEntity.ok(empresaBrainService.findAll().stream()
                .map(empresaBrainDtoMapper::toResponse)
                .toList());
    }

    @GetMapping("/{empresaId}")
    public ResponseEntity<EmpresaBrainResponse> findById(@PathVariable UUID empresaId) {
        try {
            return ResponseEntity.ok(empresaBrainDtoMapper.toResponse(
                    empresaBrainService.findById(empresaId)));
        } catch (EmpresaBrainException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
    }

    @PostMapping
    public ResponseEntity<EmpresaBrainResponse> create(@Valid @RequestBody EmpresaBrainRequest request) {
        var empresa = empresaBrainService.create(empresaBrainDtoMapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(empresaBrainDtoMapper.toResponse(empresa));
    }

    @PutMapping("/{empresaId}")
    public ResponseEntity<EmpresaBrainResponse> update(
            @PathVariable UUID empresaId,
            @Valid @RequestBody EmpresaBrainRequest request) {
        try {
            return ResponseEntity.ok(empresaBrainDtoMapper.toResponse(
                    empresaBrainService.update(empresaId, empresaBrainDtoMapper.toDomain(request))));
        } catch (EmpresaBrainException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
    }
}
