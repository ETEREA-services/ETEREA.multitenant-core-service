package eterea.tenant.service.tenant.client.core;

import eterea.tenant.service.tenant.kotlin.model.ArticuloBarra;

import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ArticuloBarraClient {

    @GetMapping("/articulo/{articuloId}")
    List<ArticuloBarra> findAllByArticuloId(@PathVariable String articuloId);

    @GetMapping("/{codigobarras}")
    ArticuloBarra findByCodigoBarras(@PathVariable String codigobarras);

    @DeleteMapping("/{codigoBarras}")
    void delete(@PathVariable String codigoBarras);

    @PostMapping
    ArticuloBarra add(@RequestBody ArticuloBarra articuloBarra);
    
}
