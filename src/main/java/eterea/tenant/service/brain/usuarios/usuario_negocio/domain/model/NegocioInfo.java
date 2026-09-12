package eterea.tenant.service.brain.usuarios.usuario_negocio.domain.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NegocioInfo {

    private Integer negocioId;
    private UUID empresaId;
    private String nombre;
    private String tenantId;
}
