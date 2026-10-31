package eterea.tenant.service.modules.stock.legacy;

import com.fasterxml.jackson.annotation.JsonFormat;
import eterea.tenant.service.modules.stock.articulo.infrastructure.persistence.entity.ArticuloEntity;
import eterea.tenant.service.tool.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class Inventario extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clave")
    private Long inventarioId;

    @Column(name = "inv_fecha")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fecha;

    @Column(name = "inv_int_id")
    private Integer inventarioTurnoId;

    @Column(name = "inv_cst_id")
    private Integer centroStockId;

    @Column(name = "inv_art_id")
    private String articuloId;

    @Column(name = "inv_cantidad")
    @Builder.Default
    private BigDecimal cantidad = new BigDecimal("0.000");

    @Column(name = "inv_stock")
    @Builder.Default
    private BigDecimal stock = new BigDecimal("0.000");

    @OneToOne
    @JoinColumn(name = "inv_int_id", updatable = false, insertable = false)
    private InventarioTurno inventarioTurno;

    @OneToOne
    @JoinColumn(name = "inv_cst_id", updatable = false, insertable = false)
    private CentroStock centroStock;

    @OneToOne
    @JoinColumn(name = "inv_art_id", updatable = false, insertable = false)
    private ArticuloEntity articulo;

}
