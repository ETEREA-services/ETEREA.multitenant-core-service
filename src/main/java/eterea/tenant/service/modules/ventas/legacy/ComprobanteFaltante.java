package eterea.tenant.service.modules.ventas.legacy;

import com.fasterxml.jackson.annotation.JsonFormat;
import eterea.tenant.service.modules.ventas.comprobante.infrastructure.persistence.entity.ComprobanteEntity;
import eterea.tenant.service.tool.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "comprobfaltante",
uniqueConstraints = {@UniqueConstraint(columnNames = {"cfa_neg_id", "cfa_cmp_id", "cfa_fecha", "cfa_prefijo", "cfa_numero"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class ComprobanteFaltante extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cfa_id")
    private Long comprobanteFaltanteId;

    @Column(name = "cfa_neg_id")
    private Integer negocioId;

    @Column(name = "cfa_cmp_id")
    private Integer comprobanteId;

    @Column(name = "cfa_fecha")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fecha;

    @Column(name = "cfa_prefijo")
    @Builder.Default
    private int prefijo = 0;

    @Column(name = "cfa_numero")
    @Builder.Default
    private long numero = 0;

    @OneToOne(optional = true)
    @JoinColumn(name = "cfa_cmp_id", insertable = false, updatable = false)
    private ComprobanteEntity comprobante;

}
