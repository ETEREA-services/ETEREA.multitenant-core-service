package eterea.tenant.service.modules.contable.legacy;

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
@Table(
name = "movconapertura",
uniqueConstraints = {@UniqueConstraint(columnNames = {"mca_fecha", "mca_nrocomp", "mca_item"})}
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class CuentaMovimientoApertura extends Auditable {

    @Column(name = "mca_fecha")
    @JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "yyyy-MM-dd'T'HH:mm:ssXX",
        timezone = "UTC"
    )
    private OffsetDateTime fecha;

    @Column(name = "mca_nrocomp")
    private Integer orden;

    @Column(name = "mca_item")
    private Integer item;

    @Column(name = "mca_cuenta")
    private Long numeroCuenta;

    @Column(name = "mca_concepto")
    private String concepto;

    @Column(name = "mca_neg_id")
    private Integer negocioId;

    @Column(name = "mca_debita")
    private Byte debita;

    @Column(name = "mca_importe")
    @Builder.Default
    private BigDecimal importe = BigDecimal.ZERO;

    @Id
    @Column(name = "clave")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cuentaMovimientoAperturaId;

}
