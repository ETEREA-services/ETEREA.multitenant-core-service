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
name = "grupoproducto",
uniqueConstraints = {@UniqueConstraint(columnNames = {"grp_gru_id", "grp_prd_id"})}
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class GrupoProducto extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grp_id")
    private Long grupoProductoId;

    @Column(name = "grp_gru_id")
    private Integer grupoId;

    @Column(name = "grp_prd_id")
    private Integer productoId;

    @Column(name = "grp_coeficiente")
    @Builder.Default
    private BigDecimal coeficiente = BigDecimal.ZERO;

}
