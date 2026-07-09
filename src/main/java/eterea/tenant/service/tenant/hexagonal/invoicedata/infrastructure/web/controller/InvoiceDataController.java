package eterea.tenant.service.tenant.hexagonal.invoicedata.infrastructure.web.controller;

import eterea.tenant.service.tenant.hexagonal.invoicedata.application.exception.InvoiceDataException;
import eterea.tenant.service.tenant.hexagonal.invoicedata.application.service.InvoiceDataService;
import eterea.tenant.service.tenant.hexagonal.invoicedata.domain.model.InvoiceData;
import eterea.tenant.service.tenant.hexagonal.invoicedata.infrastructure.dto.InvoiceDataResponse;
import eterea.tenant.service.tenant.hexagonal.invoicedata.infrastructure.mapper.InvoiceDataMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/tenant/core/invoiceData")
public class InvoiceDataController {

    private final InvoiceDataService service;
    private final InvoiceDataMapper mapper;

    public InvoiceDataController(InvoiceDataService service, InvoiceDataMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/{clienteMovimientoId}")
    public ResponseEntity<InvoiceDataResponse> getInvoiceDatabyClienteMovimientoId(@PathVariable Long clienteMovimientoId) {
        try {
            InvoiceData domain = service.getInvoiceData(clienteMovimientoId);
            return ResponseEntity.ok(mapper.toResponse(domain));
        } catch (InvoiceDataException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
