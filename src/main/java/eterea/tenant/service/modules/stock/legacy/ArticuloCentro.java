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
@Table(
name = "articuloscentro",
uniqueConstraints = {@UniqueConstraint(columnNames = {"arc_art_id", "arc_cst_id"})}
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class ArticuloCentro extends Auditable {

    @Id
    @Column(name = "arc_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articuloCentroId;

    @Column(name = "arc_art_id")
    private String articuloId;

    @Column(name = "arc_cst_id")
    private Integer centroStockId;

    @Column(name = "arc_saldo")
    @Builder.Default
    private BigDecimal saldo = BigDecimal.ZERO;

    @Column(name = "arc_saldosf")
    @Builder.Default
    private BigDecimal saldoStockFicha = BigDecimal.ZERO;

}
