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
@Table(name = "reservaorigen")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class ReservaOrigen extends Auditable {

    @Id
    @Column(name = "reo_id")
    private Integer reservaOrigenId;

    @Column(name = "reo_nombre")
    private String nombre;

}
