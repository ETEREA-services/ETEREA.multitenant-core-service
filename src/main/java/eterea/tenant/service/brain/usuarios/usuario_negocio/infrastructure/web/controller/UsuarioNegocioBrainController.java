package eterea.tenant.service.brain.usuarios.usuario_negocio.infrastructure.web.controller;

import eterea.tenant.service.brain.usuarios.usuario_negocio.application.exception.UsuarioNegocioBrainException;
import eterea.tenant.service.brain.usuarios.usuario_negocio.application.service.UsuarioNegocioBrainService;
import eterea.tenant.service.brain.usuarios.usuario_negocio.infrastructure.web.dto.UsuarioNegocioBrainRequest;
import eterea.tenant.service.brain.usuarios.usuario_negocio.infrastructure.web.dto.UsuarioNegocioBrainResponse;
import eterea.tenant.service.brain.usuarios.usuario_negocio.infrastructure.web.dto.UsuarioNegocioConNegocioResponse;
import eterea.tenant.service.brain.usuarios.usuario_negocio.infrastructure.web.mapper.UsuarioNegocioBrainDtoMapper;
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
@RequestMapping("/api/brain/core/usuario-negocio")
@RequiredArgsConstructor
public class UsuarioNegocioBrainController {

    private final UsuarioNegocioBrainService usuarioNegocioBrainService;
    private final UsuarioNegocioBrainDtoMapper usuarioNegocioBrainDtoMapper;

    @PostMapping
    public ResponseEntity<UsuarioNegocioBrainResponse> create(
            @Valid @RequestBody UsuarioNegocioBrainRequest request) {
        try {
            var usuarioNegocio = usuarioNegocioBrainService.create(
                    usuarioNegocioBrainDtoMapper.toDomain(request));
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(usuarioNegocioBrainDtoMapper.toResponse(usuarioNegocio));
        } catch (UsuarioNegocioBrainException exception) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage(), exception);
        }
    }

    @GetMapping("/usuario/{usuarioId}/empresa/{empresaId}")
    public ResponseEntity<List<UsuarioNegocioConNegocioResponse>> findAllByUsuarioIdAndEmpresaId(
            @PathVariable UUID usuarioId,
            @PathVariable UUID empresaId) {
        return ResponseEntity.ok(usuarioNegocioBrainService
                .findAllByUsuarioIdAndEmpresaId(usuarioId, empresaId).stream()
                .map(usuarioNegocioBrainDtoMapper::toConNegocioResponse)
                .toList());
    }

    @PutMapping("/{usuarioNegocioId}/deactivate")
    public ResponseEntity<UsuarioNegocioBrainResponse> deactivate(
            @PathVariable UUID usuarioNegocioId) {
        try {
            return ResponseEntity.ok(usuarioNegocioBrainDtoMapper.toResponse(
                    usuarioNegocioBrainService.deactivate(usuarioNegocioId)));
        } catch (UsuarioNegocioBrainException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
    }

    @PutMapping("/{usuarioNegocioId}/activate")
    public ResponseEntity<UsuarioNegocioBrainResponse> activate(
            @PathVariable UUID usuarioNegocioId) {
        try {
            return ResponseEntity.ok(usuarioNegocioBrainDtoMapper.toResponse(
                    usuarioNegocioBrainService.activate(usuarioNegocioId)));
        } catch (UsuarioNegocioBrainException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
    }
}
