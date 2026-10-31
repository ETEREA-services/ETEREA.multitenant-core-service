package eterea.tenant.service.modules.reserva.legacy;

import eterea.tenant.service.tool.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(
name = "habitaciontarifa",
uniqueConstraints = {@UniqueConstraint(columnNames = {"hta_numero", "hta_paxs"})}
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class HabitacionTarifa extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hta_id")
    private Long habitacionTarifaId;

    @Column(name = "hta_numero")
    @Builder.Default
    private int numero = 0;

    @Column(name = "hta_paxs")
    @Builder.Default
    private int paxs = 0;

    @Column(name = "hta_hat_id")
    @Builder.Default
    private int habitacionTipoId = 0;

}
