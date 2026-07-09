/**
 * 
 */
package eterea.tenant.service.tenant.hexagonal.legajo.infrastructure.web.controller;

import eterea.tenant.service.tenant.hexagonal.legajo.application.exception.LegajoException;

import java.net.URI;
import java.util.List;

import eterea.tenant.service.tenant.hexagonal.legajo.application.service.LegajoService;
import eterea.tenant.service.tenant.hexagonal.legajo.domain.model.Legajo;
import eterea.tenant.service.tenant.hexagonal.legajo.infrastructure.web.dto.LegajoRequest;
import eterea.tenant.service.tenant.hexagonal.legajo.infrastructure.web.dto.LegajoResponse;
import eterea.tenant.service.tenant.hexagonal.legajo.infrastructure.web.mapper.LegajoDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/tenant/core/legajo")
@RequiredArgsConstructor
public class LegajoController {

    private final LegajoDtoMapper legajoDtoMapper;
    private final LegajoService legajoService;

    @GetMapping("/")
    public ResponseEntity<List<LegajoResponse>> findAll() {
        List<Legajo> legajos = legajoService.getAllLegajos();
        return ResponseEntity.ok(legajos.stream()
                .map(legajoDtoMapper::toResponse)
                .toList());
    }

    @GetMapping("/{legajoId}")
    public ResponseEntity<LegajoResponse> findByLegajoId(@PathVariable Integer legajoId) {
        return legajoService.findByLegajoId(legajoId)
                .map(legajo -> ResponseEntity.ok(legajoDtoMapper.toResponse(legajo)))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, new LegajoException(legajoId).getMessage()));
    }

	@PostMapping("/search")
    public ResponseEntity<List<LegajoResponse>> findAllContainingSubstrings(@RequestBody List<String> substrings) {
        return ResponseEntity.ok(legajoService.findAllContainingSubstrings(substrings)
                .stream()
                .map(legajoDtoMapper::toResponse)
                .toList());
    }

    @PostMapping("/")
    public ResponseEntity<LegajoResponse> createLegajo(@RequestBody LegajoRequest legajoRequest) {
        Legajo created = legajoService.createLegajo(legajoDtoMapper.toDomain(legajoRequest));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{legajoId}")
                .buildAndExpand(created.getLegajoId())
                .toUri();
        return ResponseEntity.created(location).body(legajoDtoMapper.toResponse(created));
    }

}