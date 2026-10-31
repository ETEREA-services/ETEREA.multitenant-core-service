package eterea.tenant.service.brain.auth.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
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
public class BrainAuthLoginRequest {

    @NotBlank
    private String login;

    @NotBlank
    private String password;

    /**
     * Módulo que inicia sesión: {@code contable} o {@code administrador}.
     * Si es administrador, el usuario debe tener la bandera correspondiente.
     */
    private String app;

}
