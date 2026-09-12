package eterea.tenant.service.brain.usuarios.usuario_negocio.infrastructure.web.dto;

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
public class NegocioResponse {

    private Integer negocioId;
    private UUID empresaId;
    private String nombre;
    private String tenantId;
}
