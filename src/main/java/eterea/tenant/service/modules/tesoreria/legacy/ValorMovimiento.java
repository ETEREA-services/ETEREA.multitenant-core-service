package eterea.tenant.service.modules.tesoreria.legacy;

import com.fasterxml.jackson.annotation.JsonFormat;
import eterea.tenant.service.tool.Auditable;
import eterea.tenant.service.tool.Jsonifier;
import eterea.tenant.service.tool.Jsonifyable;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "valores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class ValorMovimiento extends Auditable implements Jsonifyable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clave")
    private Long valorMovimientoId;

    @Column(name = "val_neg_id")
    private Integer negocioId;

    @Column(name = "codigo")
    private Integer valorId;

    @Column(name = "cgoprov")
    private Long proveedorId;

    @Column(name = "cgocli")
    private Long clienteId;

    @Column(name = "fechaemi")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaEmision;

    @Column(name = "fechavto")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaVencimiento;

    @Column(name = "val_tco_id")
    private Integer comprobanteId;

    @Column(name = "nrocomprob")
    private Long numeroComprobante;

    @Builder.Default
    private BigDecimal importe = BigDecimal.ZERO;

    @Column(name = "cgocontable")
    private Long numeroCuenta;

    @Column(name = "fechareg")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaContable;

    @Column(name = "nrocompcontable")
    @Builder.Default
    private int ordenContable = 0;

    @Column(name = "clavemovp")
    private Long proveedorMovimientoId;

    @Column(name = "clavemovv")
    private Long clienteMovimientoId;

    private String titular;

    private String banco;

    private String receptor;

    @Column(name = "cgoestado")
    private Integer estadoId;

    @Column(name = "fechaentrega")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaEntrega;

    @Builder.Default
    private long tanda = 0;

    @Column(name = "tandaindex")
    @Builder.Default
    private long tandaIndex = 0;

    @Column(name = "val_cic_id")
    private Long cierreCajaId;

    @Column(name = "val_nivel")
    @Builder.Default
    private int nivel = 0;

    private String observaciones;

    private String trackUuid;

    @OneToOne(optional = true)
    @JoinColumn(name = "codigo", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Valor valor;


}
