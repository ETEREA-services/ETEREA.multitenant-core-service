package eterea.tenant.service.brain.usuarios.usuario.infrastructure.web.controller;

import eterea.tenant.service.brain.usuarios.usuario.application.exception.UsuarioBrainException;
import eterea.tenant.service.brain.usuarios.usuario.application.service.UsuarioBrainService;
import eterea.tenant.service.brain.usuarios.usuario.domain.ports.in.ResetUsuarioBrainPasswordUseCase;
import eterea.tenant.service.brain.usuarios.usuario.infrastructure.web.dto.UsuarioBrainRequest;
import eterea.tenant.service.brain.usuarios.usuario.infrastructure.web.dto.UsuarioBrainResponse;
import eterea.tenant.service.brain.usuarios.usuario.infrastructure.web.dto.ChangeUsuarioBrainPasswordRequest;
import eterea.tenant.service.brain.usuarios.usuario.infrastructure.web.dto.ResetUsuarioBrainPasswordResponse;
import eterea.tenant.service.brain.usuarios.usuario.infrastructure.web.mapper.UsuarioBrainDtoMapper;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api/brain/core/usuario")
@RequiredArgsConstructor
public class UsuarioBrainController {

    private final UsuarioBrainService usuarioBrainService;
    private final UsuarioBrainDtoMapper usuarioBrainDtoMapper;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UsuarioBrainResponse>> findAll() {
        return ResponseEntity.ok(usuarioBrainService.findAll().stream()
                .map(usuarioBrainDtoMapper::toResponse)
                .toList());
    }

    @GetMapping("/{usuarioId}")
    @PreAuthorize("hasAuthority('ADMIN') or #usuarioId.toString() == authentication.name")
    public ResponseEntity<UsuarioBrainResponse> findById(@PathVariable UUID usuarioId) {
        try {
            return ResponseEntity.ok(usuarioBrainDtoMapper.toResponse(
                    usuarioBrainService.findById(usuarioId)));
        } catch (UsuarioBrainException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UsuarioBrainResponse> create(
            @Valid @RequestBody UsuarioBrainRequest request) {
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La password es obligatoria");
        }
        UsuarioBrainResponse response = usuarioBrainDtoMapper.toResponse(
                usuarioBrainService.create(usuarioBrainDtoMapper.toDomain(request), request.getPassword()));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{usuarioId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UsuarioBrainResponse> update(
            @PathVariable UUID usuarioId,
            @Valid @RequestBody UsuarioBrainRequest request) {
        try {
            return ResponseEntity.ok(usuarioBrainDtoMapper.toResponse(usuarioBrainService.update(
                    usuarioId, usuarioBrainDtoMapper.toDomain(request), request.getPassword())));
        } catch (UsuarioBrainException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
    }

    @PostMapping("/{usuarioId}/password-reset")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResetUsuarioBrainPasswordResponse> resetPassword(
            @PathVariable UUID usuarioId) {
        try {
            ResetUsuarioBrainPasswordUseCase.TemporaryPassword temporal =
                    usuarioBrainService.resetPassword(usuarioId);
            return ResponseEntity.ok(ResetUsuarioBrainPasswordResponse.builder()
                    .temporaryPassword(temporal.temporaryPassword())
                    .expiresAt(temporal.expiresAt())
                    .build());
        } catch (UsuarioBrainException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
    }

    @PutMapping("/{usuarioId}/password")
    @PreAuthorize("hasAuthority('ADMIN') or #usuarioId.toString() == authentication.name")
    public ResponseEntity<Void> changePassword(
            @PathVariable UUID usuarioId,
            @Valid @RequestBody ChangeUsuarioBrainPasswordRequest request) {
        try {
            usuarioBrainService.changePassword(usuarioId, request.getCurrentPassword(), request.getNewPassword());
            return ResponseEntity.noContent().build();
        } catch (UsuarioBrainException exception) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No se pudo cambiar la password");
        }
    }

}
