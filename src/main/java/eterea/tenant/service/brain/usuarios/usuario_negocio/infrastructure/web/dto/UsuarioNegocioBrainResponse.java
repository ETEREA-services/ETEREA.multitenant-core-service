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
public class UsuarioNegocioBrainResponse {

    private UUID usuarioNegocioId;
    private UUID usuarioId;
    private Integer negocioId;
    @Builder.Default
    private Boolean activo = Boolean.TRUE;
}
