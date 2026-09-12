package eterea.tenant.service.brain.usuarios.usuario_empresa.infrastructure.web.dto;

import jakarta.validation.constraints.NotNull;
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
public class UsuarioEmpresaBrainRequest {

    @NotNull
    private UUID usuarioId;

    @NotNull
    private UUID empresaId;
}
