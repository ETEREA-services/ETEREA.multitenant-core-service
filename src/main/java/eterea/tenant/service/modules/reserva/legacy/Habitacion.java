package eterea.tenant.service.modules.reserva.legacy;

import eterea.tenant.service.tool.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class Habitacion extends Auditable {

    @Id
    @Column(name = "hab_numero")
    private Integer numero;

    @Column(name = "hab_hat_id")
    private Integer habitacionTipoId;

    @Column(name = "hab_cli_id")
    private Long clienteId;

}
