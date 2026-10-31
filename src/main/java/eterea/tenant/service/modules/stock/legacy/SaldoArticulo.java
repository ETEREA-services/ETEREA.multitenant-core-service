package eterea.tenant.service.modules.stock.legacy;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "vw_saldoarticulo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class SaldoArticulo {

    @Id
    private String uniqueId;

    @Column(name = "depositoid")
    private Integer centroStockId;

    @Column(name = "articuloid")
    private String articuloId;

    @Builder.Default
    private BigDecimal cantidad = BigDecimal.ZERO;

}
