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
@Table(name = "factrenddet", uniqueConstraints = {@UniqueConstraint(columnNames = {"frd_neg_id", "frd_frc_id", "frd_prv_id", "frd_cmp_id", "frd_prefijo", "frd_numero"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class FacturaRendicionDetalle extends Auditable {

    @Id
    @Column(name = "clave")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facturaRendicionDetalleId;

    @Column(name = "frd_neg_id")
    private Integer negocioId;

    @Column(name = "frd_frc_id")
    private Long facturaRendicionCabeceraId;

    @Column(name = "frd_prv_id")
    private Long proveedorId;

    @Column(name = "frd_cmp_id")
    private Integer comprobanteId;

    @Column(name = "frd_prefijo")
    @Builder.Default
    private int prefijo = 0;

    @Column(name = "frd_numero")
    @Builder.Default
    private long numeroComprobante = 0L;

    @Column(name = "frd_proveedor")
    @Builder.Default
    private String nombreProveedor = "";

    @Column(name = "frd_comprobante")
    @Builder.Default
    private String nombreComprobante = "";

    @Column(name = "frd_fecha")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaComprobante;

    @Column(name = "frd_importe")
    @Builder.Default
    private BigDecimal importe = BigDecimal.ZERO;

    @Column(name = "frd_pendiente")
    @Builder.Default
    private byte pendiente = 0;

    public String comprobanteKey() {
        return proveedorId + "." + comprobanteId + "." + prefijo + "." + numeroComprobante;
    }
}
