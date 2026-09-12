package eterea.tenant.service.brain.usuarios.usuario_empresa.infrastructure.web.dto;

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
public class UsuarioEmpresaBrainResponse {

    private UUID usuarioEmpresaId;
    private UUID usuarioId;
    private UUID empresaId;
    @Builder.Default
    private Boolean activo = Boolean.TRUE;
}
