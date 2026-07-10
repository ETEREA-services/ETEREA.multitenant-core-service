package eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.infrastructure.persistence.entity;

import eterea.tenant.service.unificado.hexagonal.negocioUnificado.infrastructure.persistence.entity.NegocioUnificadoEntity;
import eterea.tenant.service.tenant.model.Auditable;
import eterea.tenant.service.unificado.hexagonal.usuarioUnificado.infrastructure.persistence.entity.UsuarioUnificadoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuario_negocio", uniqueConstraints = @UniqueConstraint(columnNames = {"usuarioId", "negocioId"}))
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioNegocioUnificadoEntity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer usuarioNegocioId;

    private Long usuarioId;
    private Integer negocioId;

    @OneToOne(optional = true)
    @JoinColumn(name = "negocioId", referencedColumnName = "Neg_ID", insertable = false, updatable = false)
    private NegocioUnificadoEntity negocio;

    @OneToOne(optional = true)
    @JoinColumn(name = "usuarioId", referencedColumnName = "clave", insertable = false, updatable = false)
    private UsuarioUnificadoEntity usuario;

}
