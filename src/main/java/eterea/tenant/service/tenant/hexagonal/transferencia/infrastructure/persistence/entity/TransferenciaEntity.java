package eterea.tenant.service.tenant.hexagonal.transferencia.infrastructure.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import eterea.tenant.service.tenant.hexagonal.comprobante.infrastructure.persistence.entity.ComprobanteEntity;
import eterea.tenant.service.tenant.model.Auditable;
import eterea.tenant.service.tool.Jsonifier;
import eterea.tenant.service.tool.Jsonifyable;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "transferencias", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"tra_dneg_id", "tra_hneg_id", "tra_id"})
})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferenciaEntity extends Auditable implements Jsonifyable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clave")
    private Long transferenciaId;

    @Column(name = "tra_dneg_id")
    private Integer negocioIdDesde;

    @Column(name = "tra_hneg_id")
    private Integer negocioIdHasta;

    @Column(name = "tra_id")
    private Long numeroTransferencia;

    @Column(name = "tra_fecha")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fecha;

    @Column(name = "tra_total")
    @Builder.Default
    private BigDecimal total = BigDecimal.ZERO;

    @Column(name = "tra_nrocontable")
    private Integer ordenContable;

    @Column(name = "tra_transferido")
    @Builder.Default
    private Byte transferido = 0;

    @Column(name = "tra_cmp_id")
    private Integer comprobanteId;

    @Column(name = "tra_fechaotro")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaOtro;

    @OneToOne(optional = true)
    @JoinColumn(name = "tra_cmp_id", insertable = false, updatable = false)
    private ComprobanteEntity comprobante;

}
