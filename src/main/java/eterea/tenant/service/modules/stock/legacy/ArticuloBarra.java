package eterea.tenant.service.modules.stock.legacy;

import eterea.tenant.service.modules.stock.articulo.infrastructure.persistence.entity.ArticuloEntity;
import eterea.tenant.service.tool.Auditable;
import eterea.tenant.service.tool.Jsonifier;
import eterea.tenant.service.tool.Jsonifyable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "articulosbarras", uniqueConstraints = {@UniqueConstraint(columnNames = {"aba_codigo"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class ArticuloBarra extends Auditable implements Jsonifyable {

    @Id
    @Column(name = "clave")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articuloBarraId;

    @Column(name = "aba_codigo")
    private String codigoBarras;

    @Column(name = "aba_art_id")
    private String articuloId;

    @OneToOne(optional = true)
    @JoinColumn(name = "aba_art_id", updatable = false, insertable = false)
    private ArticuloEntity articulo;

}
