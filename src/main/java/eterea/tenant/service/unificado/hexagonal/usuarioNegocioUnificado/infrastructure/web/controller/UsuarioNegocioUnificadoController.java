package eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.infrastructure.web.controller;

import eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.application.exception.UsuarioNegocioUnificadoException;
import eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.application.service.UsuarioNegocioUnificadoService;
import eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.domain.model.UsuarioNegocioUnificado;
import eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.infrastructure.web.dto.UsuarioNegocioUnificadoResponse;
import eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.infrastructure.web.mapper.UsuarioNegocioUnificadoDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/unificado/core/usuarioNegocioUnificado")
@RequiredArgsConstructor
public class UsuarioNegocioUnificadoController {

    private final UsuarioNegocioUnificadoService service;
    private final UsuarioNegocioUnificadoDtoMapper mapper;

    @GetMapping
    public ResponseEntity<List<UsuarioNegocioUnificadoResponse>> findAll() {
        List<UsuarioNegocioUnificadoResponse> responses = service.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/{usuarioNegocioId}")
    public ResponseEntity<UsuarioNegocioUnificadoResponse> findByUsuarioNegocioId(@PathVariable Integer usuarioNegocioId) {
        try {
            UsuarioNegocioUnificado domain = service.findByUsuarioNegocioId(usuarioNegocioId);
            return new ResponseEntity<>(mapper.toResponse(domain), HttpStatus.OK);
        } catch (UsuarioNegocioUnificadoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<UsuarioNegocioUnificadoResponse>> findByUsuarioId(@PathVariable Long usuarioId) {
        List<UsuarioNegocioUnificadoResponse> responses = service.findByUsuarioId(usuarioId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

}
