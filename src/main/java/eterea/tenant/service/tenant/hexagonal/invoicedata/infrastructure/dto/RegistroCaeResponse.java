package eterea.tenant.service.tenant.hexagonal.invoicedata.infrastructure.dto;

import eterea.tenant.service.tool.Jsonifier;
import eterea.tenant.service.tool.Jsonifyable;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class RegistroCaeResponse implements Jsonifyable {

    private Integer tipoDocumento;
    private Integer puntoVenta;
    private Integer comprobanteId;
    private Long numeroComprobante;
    private BigDecimal total;
    private BigDecimal numeroDocumento;
    private String cae;
    private String caeVencimiento;
    private String fecha;
    private ComprobanteResponse comprobante;

}
