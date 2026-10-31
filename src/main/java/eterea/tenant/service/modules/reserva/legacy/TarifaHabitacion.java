package eterea.tenant.service.modules.reserva.legacy;

import eterea.tenant.service.tool.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tarifashabitaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class TarifaHabitacion extends Auditable {

    @Id
    @Column(name = "codigo")
    private Long tarifaHabitacionId;

    @Builder.Default
    private String descripcion = "";

    @Builder.Default
    private BigDecimal precio = BigDecimal.ZERO;

    @Column(name = "bloqueofactura")
    @Builder.Default
    private byte bloqueoFactura = 0;

    @Column(name = "minimoanticipo")
    @Builder.Default
    private BigDecimal minimoAnticipo = BigDecimal.ZERO;

}
