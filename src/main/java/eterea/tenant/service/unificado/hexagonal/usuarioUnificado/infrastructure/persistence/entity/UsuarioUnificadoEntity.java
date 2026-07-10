package eterea.tenant.service.unificado.hexagonal.usuarioUnificado.infrastructure.persistence.entity;

import eterea.tenant.service.tenant.model.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioUnificadoEntity extends Auditable {

    @Id
    @Column(name = "clave", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer usuarioId;

    @Column(name = "Usu_Nombre")
    private String login;

    @Column(name = "Usu_Password")
    private String password;

}
