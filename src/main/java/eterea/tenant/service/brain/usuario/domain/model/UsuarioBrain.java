package eterea.tenant.service.brain.usuario.domain.model;

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
public class UsuarioBrain {

    private UUID usuarioId;
    private String login;
    private String nombre;
    private String email;
    private String passwordHash;
    @Builder.Default
    private Boolean activo = true;
    private OffsetDateTime lastLoginAt;
    private String lastLoginIp;
    @Builder.Default
    private Short failedLoginAttempts = 0;
    private OffsetDateTime lockedUntil;
    private OffsetDateTime passwordChangedAt;
}
