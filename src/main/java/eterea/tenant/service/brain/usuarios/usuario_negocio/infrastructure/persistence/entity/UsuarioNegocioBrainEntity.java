package eterea.tenant.service.brain.usuarios.usuario_negocio.infrastructure.persistence.entity;

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
@Table(name = "usuario_negocio", uniqueConstraints = {
        @UniqueConstraint(name = "uq_usuario_negocio", columnNames = {"usuario_id", "negocio_id"})
}, indexes = {
        @Index(name = "idx_negocio_id", columnList = "negocio_id")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioNegocioBrainEntity extends Auditable {

    @Id
    @Column(name = "usuario_negocio_id", nullable = false, updatable = false)
    private UUID usuarioNegocioId;

    @Column(name = "usuario_id", nullable = false, updatable = false)
    private UUID usuarioId;

    @Column(name = "negocio_id", nullable = false)
    private Integer negocioId;

    @ColumnDefault("1")
    @Column(name = "activo", nullable = false)
    @Builder.Default
    private Boolean activo = Boolean.TRUE;

}
