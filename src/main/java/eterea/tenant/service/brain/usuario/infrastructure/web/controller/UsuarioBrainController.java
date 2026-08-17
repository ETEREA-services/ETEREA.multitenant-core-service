package eterea.tenant.service.brain.usuario.infrastructure.web.controller;

import eterea.tenant.service.brain.usuario.application.exception.UsuarioBrainException;
import eterea.tenant.service.brain.usuario.application.service.UsuarioBrainService;
import eterea.tenant.service.brain.usuario.infrastructure.web.dto.UsuarioBrainLoginRequest;
import eterea.tenant.service.brain.usuario.infrastructure.web.dto.UsuarioBrainRequest;
import eterea.tenant.service.brain.usuario.infrastructure.web.dto.UsuarioBrainResponse;
import eterea.tenant.service.brain.usuario.infrastructure.web.dto.ChangeUsuarioBrainPasswordRequest;
import eterea.tenant.service.brain.usuario.infrastructure.web.mapper.UsuarioBrainDtoMapper;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import jakarta.servlet.http.HttpServletRequest;
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
@RequestMapping("/api/brain/core/usuario")
@RequiredArgsConstructor
public class UsuarioBrainController {

    private final UsuarioBrainService usuarioBrainService;
    private final UsuarioBrainDtoMapper usuarioBrainDtoMapper;

    @GetMapping
    public ResponseEntity<List<UsuarioBrainResponse>> findAll() {
        return ResponseEntity.ok(usuarioBrainService.findAll().stream()
                .map(usuarioBrainDtoMapper::toResponse)
                .toList());
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<UsuarioBrainResponse> findById(@PathVariable UUID usuarioId) {
        try {
            return ResponseEntity.ok(usuarioBrainDtoMapper.toResponse(
                    usuarioBrainService.findById(usuarioId)));
        } catch (UsuarioBrainException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
    }

    @PostMapping
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

    @PostMapping("/login")
    public ResponseEntity<UsuarioBrainResponse> login(
            @Valid @RequestBody UsuarioBrainLoginRequest request,
            HttpServletRequest httpRequest) {
        try {
            return ResponseEntity.ok(usuarioBrainDtoMapper.toResponse(
                    usuarioBrainService.login(request.getLogin(), request.getPassword(),
                            resolveClientIp(httpRequest))));
        } catch (UsuarioBrainException exception) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }
    }

    @PutMapping("/{usuarioId}/password")
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

    private String resolveClientIp(HttpServletRequest request) {
        String forwardedFor = request.getHeader("X-Forwarded-For");
        if (forwardedFor != null && !forwardedFor.isBlank()) {
            return forwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
