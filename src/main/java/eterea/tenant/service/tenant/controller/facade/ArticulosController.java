package eterea.tenant.service.tenant.controller.facade;

import eterea.tenant.service.tenant.service.facade.ArticulosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tenant/core/articulos")
@RequiredArgsConstructor
public class ArticulosController {

    private final ArticulosService service;

    @GetMapping("/code/new")
    public ResponseEntity<String> getNewCode() {
        return ResponseEntity.ok(service.getNewCode());
    }

    @GetMapping("/replicate/{articuloId}")
    public ResponseEntity<Boolean> replicate(@PathVariable String articuloId) {
        return ResponseEntity.ok(service.replicate(articuloId));
    }

}
