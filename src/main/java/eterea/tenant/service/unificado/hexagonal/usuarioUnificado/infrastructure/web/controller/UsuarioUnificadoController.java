package eterea.tenant.service.unificado.hexagonal.usuarioUnificado.infrastructure.web.controller;

import eterea.tenant.service.unificado.hexagonal.usuarioUnificado.application.exception.UsuarioUnificadoException;
import eterea.tenant.service.unificado.hexagonal.usuarioUnificado.application.service.UsuarioUnificadoService;
import eterea.tenant.service.unificado.hexagonal.usuarioUnificado.domain.model.UsuarioUnificado;
import eterea.tenant.service.unificado.hexagonal.usuarioUnificado.infrastructure.web.dto.UsuarioUnificadoResponse;
import eterea.tenant.service.unificado.hexagonal.usuarioUnificado.infrastructure.web.mapper.UsuarioUnificadoDtoMapper;
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
@RequestMapping("/api/unificado/core/usuarioUnificado")
@RequiredArgsConstructor
public class UsuarioUnificadoController {

    private final UsuarioUnificadoService service;
    private final UsuarioUnificadoDtoMapper mapper;

    @GetMapping
    public ResponseEntity<List<UsuarioUnificadoResponse>> findAll() {
        List<UsuarioUnificadoResponse> responses = service.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<UsuarioUnificadoResponse> findByUsuarioId(@PathVariable Integer usuarioId) {
        try {
            UsuarioUnificado domain = service.findByUsuarioId(usuarioId);
            return new ResponseEntity<>(mapper.toResponse(domain), HttpStatus.OK);
        } catch (UsuarioUnificadoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/login/{login}")
    public ResponseEntity<UsuarioUnificadoResponse> findByLogin(@PathVariable String login) {
        try {
            UsuarioUnificado domain = service.findByLogin(login);
            return new ResponseEntity<>(mapper.toResponse(domain), HttpStatus.OK);
        } catch (UsuarioUnificadoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
