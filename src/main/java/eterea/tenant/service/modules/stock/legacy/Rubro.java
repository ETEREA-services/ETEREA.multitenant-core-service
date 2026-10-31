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
@Table(name = "articulosrubros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class Rubro extends Auditable {

    @Id
    @Column(name = "codigo")
    private Long rubroId;

    @Column(name = "aru_neg_id")
    private Integer negocioId;

    private String descripcion;

    @Column(name = "unegocio")
    private Integer unidadNegocio;

    @Column(name = "aru_restaurant")
    @Builder.Default
    private byte restaurant = 0;

    @Column(name = "aru_prv_id")
    private Long proveedorId;

}
