package eterea.tenant.service.modules.reserva.legacy;

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
@Table(name = "habitaciontipo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class HabitacionTipo extends Auditable {

    @Id
    @Column(name = "hat_id")
    private Integer habitacionTipoId;

    @Column(name = "hat_nombre")
    @Builder.Default
    private String nombre = "";

    @Column(name = "hat_paxs")
    private Integer paxs;

}
