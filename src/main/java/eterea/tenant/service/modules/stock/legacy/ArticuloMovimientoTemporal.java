package eterea.tenant.service.modules.stock.legacy;

import eterea.tenant.service.tool.Auditable;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tmdetartic")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class ArticuloMovimientoTemporal extends Auditable {

    @Id
    @Column(name = "clave")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articuloMovimientoTemporalId;

    @Column(name = "ipaddress")
    @Builder.Default
    private String ipAddress = "";

    @Column(name = "dat_hwnd")
    @Builder.Default
    private long hWnd = 0;

    @Builder.Default
    private long item = 0;

    @Builder.Default
    private int item2 = 0;

    @Column(name = "cgocentro")
    private Long centroId;

    @Column(name = "cgoarticulo")
    private String articuloId;

    @Column(name = "clavedetartic")
    private Long articuloMovimientoId;

    private String descripcion;

    @Column(name = "preciounitario")
    @Builder.Default
    private BigDecimal precioUnitario = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal cantidad = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal total = BigDecimal.ZERO;

    @Column(name = "cgocontable")
    private Long cuenta;

    @Column(name = "preunitciva")
    @Builder.Default
    private BigDecimal precioUnitarioConIva = BigDecimal.ZERO;

    @Column(name = "preunitsiva")
    @Builder.Default
    private BigDecimal precioUnitarioSinIva = BigDecimal.ZERO;

    @Column(name = "preciocompra")
    @Builder.Default
    private BigDecimal precioCompra = BigDecimal.ZERO;

    @Builder.Default
    private byte iva105 = 0;

    @Builder.Default
    private byte exento = 0;

    @Builder.Default
    private BigDecimal comision = BigDecimal.ZERO;

}
