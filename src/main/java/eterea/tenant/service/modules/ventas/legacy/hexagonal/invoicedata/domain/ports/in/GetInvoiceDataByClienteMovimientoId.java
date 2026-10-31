package eterea.tenant.service.modules.ventas.legacy.hexagonal.invoicedata.domain.ports.in;

import eterea.tenant.service.modules.ventas.legacy.hexagonal.invoicedata.domain.model.InvoiceData;

public interface GetInvoiceDataByClienteMovimientoId {

    InvoiceData getInvoiceDataByClienteMovimientoId(Long clienteMovimientoId);

}
