package eterea.tenant.service.modules.stock.legacy;

import eterea.tenant.service.modules.stock.articulo.infrastructure.persistence.entity.ArticuloEntity;
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
@Table(name = "articulossaldofecha", uniqueConstraints = {@UniqueConstraint(columnNames = {"asf_cst_id", "asf_art_id", "asf_fecha"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class ArticuloSaldoFecha extends Auditable {

    @Id
    @Column(name = "clave")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articuloSaldoFechaId;

    @Column(name = "asf_cst_id")
    private Integer centroStockId;

    @Column(name = "asf_art_id")
    private String articuloId;

    @Column(name = "asf_fecha")
    private OffsetDateTime fecha;

    @Column(name = "asf_saldo")
    @Builder.Default
    private BigDecimal saldo = BigDecimal.ZERO;

    @OneToOne(optional = true)
    @JoinColumn(name = "asf_cst_id", insertable = false, updatable = false)
    private CentroStock centroStock;

    @OneToOne(optional = true)
    @JoinColumn(name = "asf_art_id", insertable = false, updatable = false)
    private ArticuloEntity articulo;

}
