package eterea.tenant.service.tenant.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import eterea.tenant.service.tool.Jsonifier;
import eterea.tenant.service.tool.Jsonifyable;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticuloDto implements Jsonifyable {

    private String articuloId;
    private Integer negocioId;
    private String descripcion;
    private String leyendaVoucher;
    private BigDecimal precioVentaSinIva;
    private BigDecimal precioVentaConIva;
    private Long cuentaVentas;
    private Long cuentaCompras;
    private Long cuentaGastos;
    private Integer centroStockId;
    private Long rubroId;
    private Long subRubroId;
    private BigDecimal precioCompra;
    private Byte iva105;
    private BigDecimal precioCompraNeto;
    private Byte exento;
    private Long stockMinimo;
    private Long stockOptimo;
    private Byte bloqueoCompras;
    private Byte bloqueoStock;
    private Byte bloqueoVentas;
    private Long unidadMedidaId;
    private Byte conDecimales;
    private Byte ventas;
    private Byte compras;
    private String unidadMedida;
    private Integer conversionId;
    private Byte ventaSinStock;
    private Byte controlaStock;
    private Byte asientoCostos;
    private String mascaraBalanza;
    private Byte habilitaIngreso;
    private BigDecimal comision;
    private Integer prestadorId;
    private Long autoNumericoId;

}
