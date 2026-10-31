package eterea.tenant.service.modules.stock.legacy;

import eterea.tenant.service.modules.stock.articulo.infrastructure.persistence.entity.ArticuloEntity;
import eterea.tenant.service.tool.Auditable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"articuloId"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class ArticuloListaPrecio extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID articuloListaPrecioId;

    private String articuloId;

    @Builder.Default
    private byte publicar = 0;

    @OneToOne(optional = true)
    @JoinColumn(name = "articuloId", insertable = false, updatable = false)
    private ArticuloEntity articulo;

}
