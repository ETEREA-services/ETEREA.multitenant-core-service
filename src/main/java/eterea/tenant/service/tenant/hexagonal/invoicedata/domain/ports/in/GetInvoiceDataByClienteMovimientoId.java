package eterea.tenant.service.tenant.hexagonal.invoicedata.domain.ports.in;

import eterea.tenant.service.tenant.hexagonal.invoicedata.domain.model.InvoiceData;

public interface GetInvoiceDataByClienteMovimientoId {

    InvoiceData getInvoiceDataByClienteMovimientoId(Long clienteMovimientoId);

}
