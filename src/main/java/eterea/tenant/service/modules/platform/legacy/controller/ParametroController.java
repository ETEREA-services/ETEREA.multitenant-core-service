package eterea.tenant.service.modules.platform.legacy.controller;

import eterea.tenant.service.modules.platform.legacy.Parametro;
import eterea.tenant.service.modules.platform.legacy.service.ParametroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tenant/core/parametro")
@RequiredArgsConstructor
public class ParametroController {

    private final ParametroService service;

    @GetMapping("/top")
    public ResponseEntity<Parametro> findTop() {
        return new ResponseEntity<>(service.findTop(), HttpStatus.OK);
    }

}
