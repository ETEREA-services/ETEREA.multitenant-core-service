package eterea.tenant.service.tenant.hexagonal.invoicedata.infrastructure.dto;

import eterea.tenant.service.tool.Jsonifier;
import eterea.tenant.service.tool.Jsonifyable;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class ClienteMovimientoResponse implements Jsonifyable {

    private String letraComprobante;
    private Integer puntoVenta;
    private Long numeroComprobante;
    private String observaciones;
    private String letras;
    private Long reservaId;
    private BigDecimal neto;
    private BigDecimal montoExento;
    private BigDecimal montoIva;
    private BigDecimal montoIvaRni;
    private BigDecimal importe;
    private EmpresaResponse empresa;
    private ClienteResponse cliente;
    private MonedaResponse moneda;
    private ComprobanteResponse comprobante;
    private List<ArticuloMovimientoResponse> articulos;

}
