package eterea.tenant.service.tenant.hexagonal.negocio.infrastructure.web.controller;

import eterea.tenant.service.tenant.hexagonal.negocio.application.exception.NegocioException;
import eterea.tenant.service.tenant.hexagonal.negocio.application.service.NegocioService;
import eterea.tenant.service.tenant.hexagonal.negocio.domain.model.Negocio;
import eterea.tenant.service.tenant.hexagonal.negocio.infrastructure.web.dto.NegocioResponse;
import eterea.tenant.service.tenant.hexagonal.negocio.infrastructure.web.mapper.NegocioDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/tenant/core/negocio")
public class NegocioController {

    private final NegocioService service;
    private final NegocioDtoMapper mapper;

    public NegocioController(NegocioService service, NegocioDtoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/with/ip/{negocioId}")
    public ResponseEntity<List<NegocioResponse>> getAllNegociosWithIpAddress(@PathVariable Integer negocioId) {
        List<NegocioResponse> responses = service.getAllNegociosWithIpAddress(negocioId).stream()
                .map(mapper::toResponse)
                .toList();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/{negocioId}")
    public ResponseEntity<NegocioResponse> getNegocioById(@PathVariable Integer negocioId) {
        try {
            Negocio domain = service.getNegocioById(negocioId);
            return new ResponseEntity<>(mapper.toResponse(domain), HttpStatus.OK);
        } catch (NegocioException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
