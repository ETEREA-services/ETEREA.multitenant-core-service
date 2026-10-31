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
name = "productoclientecomision",
uniqueConstraints = {@UniqueConstraint(columnNames = {"pcc_prd_id", "pcc_cli_id"})}
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class ProductoClienteComision extends Auditable {

    @Column(name = "pcc_prd_id")
    private Integer productoId;

    @jakarta.persistence.Column(name = "pcc_cli_id")
    private Long clienteId;

    @Column(name = "pcc_neg_id")
    private Integer negocioId;

    @Column(name = "pcc_comision")
    @Builder.Default
    private BigDecimal comision = BigDecimal.ZERO;

    @Id
    @Column(name = "pcc_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productoClienteComisionId;

}
