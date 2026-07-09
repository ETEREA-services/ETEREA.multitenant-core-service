package eterea.tenant.service.tenant.controller;

import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eterea.tenant.service.tenant.model.dto.ValorMovimientoDto;
import eterea.tenant.service.tenant.service.ValorMovimientoService;

@RestController
@RequestMapping("/api/tenant/core/valorMovimiento")
@RequiredArgsConstructor
public class ValorMovimientoController {

   private final ValorMovimientoService service;

   @GetMapping()
   public ResponseEntity<List<ValorMovimientoDto>> findAllValorMovimientos(
         @RequestParam LocalDate desde,
         @RequestParam LocalDate hasta,
         @RequestParam boolean cierreCajaOnly,
         @RequestParam boolean ingresosOnly) {
      return ResponseEntity.status(HttpStatus.OK)
            .body(service.findAllMovimientos(desde, hasta, cierreCajaOnly,
                  ingresosOnly));
   }

}