package eterea.tenant.service.modules.platform.legacy;

import eterea.tenant.service.tool.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class Usuario extends Auditable {

    @Id
    @Column(name = "nombre")
    private String login;

    @Column(name = "usu_descripcion")
    private String descripcion;

    @Column(name = "usu_password")
    private String password;

    @Column(name = "usu_correo")
    private String email;

    @Column(name = "usu_nivel")
    @Builder.Default
    private int nivel = 0;

    @Column(name = "usu_pin")
    private String pin;

    @Column(name = "usu_cierrectype")
    private String cierreRecipientType;

    @Column(name = "usu_consrectype")
    private String consolidadoRecipientType;

    @Column(name = "clave")
    private Long usuarioId;

}
