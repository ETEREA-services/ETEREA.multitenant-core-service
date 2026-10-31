package eterea.tenant.service.modules.stock.legacy;

import eterea.tenant.service.modules.stock.articulo.infrastructure.persistence.entity.ArticuloEntity;
import eterea.tenant.service.tool.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "productoarticulo", uniqueConstraints = {@UniqueConstraint(columnNames = {"par_prd_id", "par_art_id"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class ProductoArticulo extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "par_id")
    private Long productoarticuloId;

    @Column(name = "par_neg_id")
    private Integer negocioId;

    @Column(name = "par_prd_id")
    private Integer productoId;

    @Column(name = "par_art_id")
    private String articuloId;

    @OneToOne(optional = true)
    @JoinColumn(name = "par_prd_id", insertable = false, updatable = false)
    private Producto producto;

    @OneToOne(optional = true)
    @JoinColumn(name = "par_art_id", insertable = false, updatable = false)
    private ArticuloEntity articulo;

}
