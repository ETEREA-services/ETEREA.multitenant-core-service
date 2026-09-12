package eterea.tenant.service.brain.usuarios.usuario_negocio.infrastructure.web.dto;

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
public class UsuarioNegocioBrainRequest {

    @NotNull
    private UUID usuarioId;

    @NotNull
    private Integer negocioId;
}
