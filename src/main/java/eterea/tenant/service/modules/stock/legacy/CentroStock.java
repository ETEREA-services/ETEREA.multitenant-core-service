package eterea.tenant.service.modules.stock.legacy;

import eterea.tenant.service.tool.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "centrosstock")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class CentroStock extends Auditable {

    @Id
    @Column(name = "codigo")
    private Integer centroStockId;

    @Builder.Default
    private String descripcion = "";

    @Column(name = "cst_neg_id")
    private Integer negocioId;

    @Column(name = "cst_contable")
    private Long numeroCuenta;

    @Column(name = "cst_controlastock")
    @Builder.Default
    private byte controlaStock = 0;

    @Column(name = "cst_tipo")
    @Builder.Default
    private int tipo = 0;

}
