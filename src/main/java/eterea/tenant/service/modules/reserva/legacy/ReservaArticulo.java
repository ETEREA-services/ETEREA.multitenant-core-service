package eterea.tenant.service.modules.reserva.legacy;

import eterea.tenant.service.tool.Auditable;
import eterea.tenant.service.tool.Jsonifier;
import eterea.tenant.service.tool.Jsonifyable;
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
@Table(name = "reservaarticulo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class ReservaArticulo extends Auditable implements Jsonifyable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rar_id")
    private Long reservaArticuloId;

    @Column(name = "rar_neg_id")
    private Integer negocioId;

    @Column(name = "rar_res_id")
    private Long reservaId;

    @Column(name = "rar_vou_id")
    private Long voucherId;

    @Column(name = "rar_art_id")
    private String articuloId;

    @Column(name = "rar_cantidad")
    @Builder.Default
    private int cantidad = 0;

    @Column(name = "rar_comision")
    @Builder.Default
    private BigDecimal comision = BigDecimal.ZERO;

    @Column(name = "rar_preciounitsincomision")
    @Builder.Default
    private BigDecimal precioUnitarioSinComision = BigDecimal.ZERO;

    @Column(name = "rar_preciounitario")
    @Builder.Default
    private BigDecimal precioUnitario = BigDecimal.ZERO;

    @Column(name = "rar_preciocompra")
    @Builder.Default
    private BigDecimal precioCompra = BigDecimal.ZERO;

    @Column(name = "rar_observaciones")
    @Builder.Default
    private String observaciones = "";

    private String trackUuid;


}
