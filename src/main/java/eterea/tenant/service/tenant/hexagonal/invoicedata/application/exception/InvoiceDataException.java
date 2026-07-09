package eterea.tenant.service.tenant.hexagonal.invoicedata.application.exception;

public class InvoiceDataException extends RuntimeException {

    public InvoiceDataException() {
        super("Cannot find InvoiceData");
    }

    public InvoiceDataException(Long clienteMovimientoId) {
        super("Cannot find InvoiceData with clienteMovimientoId: " + clienteMovimientoId);
    }

}
