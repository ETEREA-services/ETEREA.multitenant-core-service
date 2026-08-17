package eterea.tenant.service.brain.usuario.infrastructure.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class UsuarioBrainRequest {

    @NotBlank
    private String login;

    @NotBlank
    private String nombre;

    @NotBlank
    @Email
    private String email;

    @Size(min = 8, max = 255)
    private String password;

    private Boolean activo;
}
