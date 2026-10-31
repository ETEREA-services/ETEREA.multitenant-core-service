package eterea.tenant.service.modules.ventas.legacy.hexagonal.invoicedata.infrastructure.dto;

import eterea.tenant.service.modules.stock.articulo.infrastructure.web.dto.ArticuloResponseForInvoiceData;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ArticuloMovimientoResponse {

    private BigDecimal cantidad;
    private BigDecimal precioUnitarioSinIva;
    private BigDecimal precioUnitarioConIva;
    private BigDecimal precioTotalSinIva;
    private BigDecimal precioTotalConIva;
    private ArticuloResponseForInvoiceData articulo;
    private ConceptoFacturadoResponse conceptoFacturado;

}
