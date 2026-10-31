package eterea.tenant.service.modules.ventas.legacy;

import com.fasterxml.jackson.annotation.JsonFormat;
import eterea.tenant.service.tool.Auditable;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "articulomovimientoprevio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class ArticuloMovimientoPrevio extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "articulomovimientoprevio_id")
    private Long articuloMovimientoPrevioId;

    @Column(name = "clientemovimientoprevio_id")
    private Long clienteMovimientoPrevioId;

    private String articuloId;

    @Builder.Default
    private BigDecimal cantidad = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal precioUnitarioSinIva = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal precioUnitarioConIva = BigDecimal.ZERO;

    private Long cuentaVenta;

    @Column(name = "iva_21")
    @Builder.Default
    private byte iva21 = 0;

    @Column(name = "iva_105")
    @Builder.Default
    private byte iva105 = 0;

    @Builder.Default
    private byte exento = 0;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaMovimiento;

    @Column(name = "articulomovimiento_id")
    private Long articuloMovimientoId;



}
