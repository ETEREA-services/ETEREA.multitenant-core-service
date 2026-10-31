package eterea.tenant.service.modules.ventas.legacy.hexagonal.invoicedata.application.exception;


import eterea.tenant.service.modules.ventas.legacy.hexagonal.invoicedata.domain.model.InvoiceData;
public class InvoiceDataException extends RuntimeException {

    public InvoiceDataException() {
        super("Cannot find InvoiceData");
    }

    public InvoiceDataException(Long clienteMovimientoId) {
        super("Cannot find InvoiceData with clienteMovimientoId: " + clienteMovimientoId);
    }

}
