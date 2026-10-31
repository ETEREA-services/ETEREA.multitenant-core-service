package eterea.tenant.service.modules.stock.legacy;

import eterea.tenant.service.tool.Auditable;
import eterea.tenant.service.tool.Jsonifier;
import eterea.tenant.service.tool.Jsonifyable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "voucherproducto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class VoucherProducto extends Auditable implements Jsonifyable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vpr_id")
    private Long voucherProductoId;

    @Column(name = "vpr_vou_id")
    private Long voucherId;

    @Column(name = "vpr_prd_id")
    private Integer productoId;

    @Column(name = "vpr_paxs")
    private Integer cantidadPaxs;

    private String trackUuid;

    @OneToOne(optional = true)
    @JoinColumn(name = "vpr_prd_id", insertable = false, updatable = false)
    private Producto producto;

}
