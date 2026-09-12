package eterea.tenant.service.brain.usuarios.usuario_empresa.infrastructure.web.controller;

import eterea.tenant.service.brain.usuarios.usuario_empresa.application.exception.UsuarioEmpresaBrainException;
import eterea.tenant.service.brain.usuarios.usuario_empresa.application.service.UsuarioEmpresaBrainService;
import eterea.tenant.service.brain.usuarios.usuario_empresa.infrastructure.web.dto.UsuarioEmpresaBrainRequest;
import eterea.tenant.service.brain.usuarios.usuario_empresa.infrastructure.web.dto.UsuarioEmpresaBrainResponse;
import eterea.tenant.service.brain.usuarios.usuario_empresa.infrastructure.web.mapper.UsuarioEmpresaBrainDtoMapper;
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
@RequestMapping("/api/brain/core/usuario-empresa")
@RequiredArgsConstructor
public class UsuarioEmpresaBrainController {

    private final UsuarioEmpresaBrainService usuarioEmpresaBrainService;
    private final UsuarioEmpresaBrainDtoMapper usuarioEmpresaBrainDtoMapper;

    @PostMapping
    public ResponseEntity<UsuarioEmpresaBrainResponse> create(
            @Valid @RequestBody UsuarioEmpresaBrainRequest request) {
        var usuarioEmpresa = usuarioEmpresaBrainService.create(
                usuarioEmpresaBrainDtoMapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioEmpresaBrainDtoMapper.toResponse(usuarioEmpresa));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<UsuarioEmpresaBrainResponse>> findAllByUsuarioId(
            @PathVariable UUID usuarioId) {
        return ResponseEntity.ok(usuarioEmpresaBrainService.findAllByUsuarioId(usuarioId).stream()
                .map(usuarioEmpresaBrainDtoMapper::toResponse)
                .toList());
    }

    @PutMapping("/{usuarioEmpresaId}/deactivate")
    public ResponseEntity<UsuarioEmpresaBrainResponse> deactivate(
            @PathVariable UUID usuarioEmpresaId) {
        try {
            return ResponseEntity.ok(usuarioEmpresaBrainDtoMapper.toResponse(
                    usuarioEmpresaBrainService.deactivate(usuarioEmpresaId)));
        } catch (UsuarioEmpresaBrainException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
    }

    @PutMapping("/{usuarioEmpresaId}/activate")
    public ResponseEntity<UsuarioEmpresaBrainResponse> activate(
            @PathVariable UUID usuarioEmpresaId) {
        try {
            return ResponseEntity.ok(usuarioEmpresaBrainDtoMapper.toResponse(
                    usuarioEmpresaBrainService.activate(usuarioEmpresaId)));
        } catch (UsuarioEmpresaBrainException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
    }
}
