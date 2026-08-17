package eterea.tenant.service.brain.usuario.infrastructure.persistence.entity;

import eterea.tenant.service.tenant.model.Auditable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "usuario", uniqueConstraints = {
        @UniqueConstraint(name = "uq_usuario_login", columnNames = "login"),
        @UniqueConstraint(name = "uq_usuario_email", columnNames = "email")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioBrainEntity extends Auditable {

    @Id
    private UUID usuarioId;

    private String login;
    private String nombre;
    private String email;
    private String passwordHash;
    private Boolean activo;
    private OffsetDateTime lastLoginAt;
    private String lastLoginIp;
    private Short failedLoginAttempts;
    private OffsetDateTime lockedUntil;
    private OffsetDateTime passwordChangedAt;

}
