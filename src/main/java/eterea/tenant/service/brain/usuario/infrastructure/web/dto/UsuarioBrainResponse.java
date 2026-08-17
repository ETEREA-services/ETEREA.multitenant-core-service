package eterea.tenant.service.brain.usuario.infrastructure.web.dto;

import java.time.OffsetDateTime;
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
public class UsuarioBrainResponse {

    private UUID usuarioId;
    private String login;
    private String nombre;
    private String email;
    private Boolean activo;
    private OffsetDateTime lastLoginAt;
    private String lastLoginIp;
    private Short failedLoginAttempts;
    private OffsetDateTime lockedUntil;
    private OffsetDateTime passwordChangedAt;
}
