package eterea.tenant.service.brain.auth.infrastructure.web.dto;

import eterea.tenant.service.brain.usuarios.usuario.infrastructure.web.dto.UsuarioBrainResponse;
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
public class AuthTokenResponse {

    private String accessToken;
    private String tokenType;
    private long expiresIn;
    private UsuarioBrainResponse usuario;

}
