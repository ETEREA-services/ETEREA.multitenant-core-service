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
public class UsuarioNegocioConNegocioBrain {

    private UUID usuarioNegocioId;
    private UUID usuarioId;
    private Integer negocioId;
    private Boolean activo;
    private NegocioInfo negocio;
}
