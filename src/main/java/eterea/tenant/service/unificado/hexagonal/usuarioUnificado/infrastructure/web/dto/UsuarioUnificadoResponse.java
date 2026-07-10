package eterea.tenant.service.unificado.hexagonal.usuarioUnificado.infrastructure.web.dto;

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
public class UsuarioUnificadoResponse {

    private Integer usuarioId;
    private String login;
    private String password;

}
