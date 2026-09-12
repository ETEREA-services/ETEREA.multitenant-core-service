package eterea.tenant.service.brain.usuarios.usuario_empresa.infrastructure.persistence.entity;

import eterea.tenant.service.tenant.model.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;

@Entity
@Table(name = "usuario_empresa", uniqueConstraints = {
        @UniqueConstraint(name = "uq_usuario_empresa", columnNames = {"usuario_id", "empresa_id"})
}, indexes = {
        @Index(name = "idx_empresa_id", columnList = "empresa_id")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEmpresaBrainEntity extends Auditable {

    @Id
    @Column(name = "usuario_empresa_id", nullable = false, updatable = false)
    private UUID usuarioEmpresaId;

    @Column(name = "usuario_id", nullable = false, updatable = false)
    private UUID usuarioId;

    @Column(name = "empresa_id", nullable = false)
    private UUID empresaId;

    @ColumnDefault("1")
    @Column(name = "activo", nullable = false)
    @Builder.Default
    private Boolean activo = Boolean.TRUE;

}
