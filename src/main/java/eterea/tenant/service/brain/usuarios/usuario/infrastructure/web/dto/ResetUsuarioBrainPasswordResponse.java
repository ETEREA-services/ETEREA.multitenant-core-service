package eterea.tenant.service.brain.usuarios.usuario.infrastructure.web.dto;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Clave temporal devuelta una única vez al administrador. */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResetUsuarioBrainPasswordResponse {

    private String temporaryPassword;
    private OffsetDateTime expiresAt;
}
