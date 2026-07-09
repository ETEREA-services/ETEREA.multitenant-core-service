package eterea.tenant.service.unificado.hexagonal.negocioUnificado.infrastructure.web.controller;

import eterea.tenant.service.unificado.hexagonal.negocioUnificado.application.exception.NegocioUnificadoException;
import eterea.tenant.service.unificado.hexagonal.negocioUnificado.application.service.NegocioUnificadoService;
import eterea.tenant.service.unificado.hexagonal.negocioUnificado.domain.model.NegocioUnificado;
import eterea.tenant.service.unificado.hexagonal.negocioUnificado.infrastructure.web.dto.NegocioUnificadoResponse;
import eterea.tenant.service.unificado.hexagonal.negocioUnificado.infrastructure.web.mapper.NegocioUnificadoDtoMapper;
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
@RequestMapping("/api/unificado/core/negocioUnificado")
@RequiredArgsConstructor
public class NegocioUnificadoController {

    private final NegocioUnificadoService service;
    private final NegocioUnificadoDtoMapper mapper;

    @GetMapping
    public ResponseEntity<List<NegocioUnificadoResponse>> findAll() {
        List<NegocioUnificadoResponse> responses = service.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/{negocioId}")
    public ResponseEntity<NegocioUnificadoResponse> findByNegocioId(@PathVariable Integer negocioId) {
        try {
            NegocioUnificado domain = service.findByNegocioId(negocioId);
            return new ResponseEntity<>(mapper.toResponse(domain), HttpStatus.OK);
        } catch (NegocioUnificadoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
