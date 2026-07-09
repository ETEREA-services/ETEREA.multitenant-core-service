package eterea.tenant.service.tenant.client.core;

import eterea.tenant.service.tenant.model.dto.ArticuloDto;
import org.springframework.web.bind.annotation.*;

public interface ArticuloClient {

    @GetMapping("/{articuloId}")
    ArticuloDto findByArticuloId(@PathVariable String articuloId);

    @PostMapping("/")
    ArticuloDto add(@RequestBody ArticuloDto articulo);

    @PutMapping("/{articuloId}")
    ArticuloDto update(@RequestBody ArticuloDto articulo, @PathVariable String articuloId);

}
