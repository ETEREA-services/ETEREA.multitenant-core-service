package eterea.tenant.service.modules.platform.legacy;

import eterea.tenant.service.tool.Auditable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Entity
@Table(name = "posicion")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PosicionIva extends Auditable {

    @Id
    private Integer posicionId;

    private String nombre;
    private String userId;
    private Integer idPosicionIvaArca;

}
