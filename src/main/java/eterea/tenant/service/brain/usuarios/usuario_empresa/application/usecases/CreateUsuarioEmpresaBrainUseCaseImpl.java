package eterea.tenant.service.brain.usuarios.usuario_empresa.application.usecases;

import eterea.tenant.service.brain.usuarios.usuario_empresa.domain.model.UsuarioEmpresaBrain;
import eterea.tenant.service.brain.usuarios.usuario_empresa.domain.ports.in.CreateUsuarioEmpresaBrainUseCase;
import eterea.tenant.service.brain.usuarios.usuario_empresa.domain.ports.out.UsuarioEmpresaBrainRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUsuarioEmpresaBrainUseCaseImpl implements CreateUsuarioEmpresaBrainUseCase {

    private final UsuarioEmpresaBrainRepository usuarioEmpresaBrainRepository;

    @Override
    public UsuarioEmpresaBrain create(UsuarioEmpresaBrain usuarioEmpresa) {
        usuarioEmpresa.setUsuarioEmpresaId(UUID.randomUUID());
        usuarioEmpresa.setActivo(Boolean.TRUE);
        return usuarioEmpresaBrainRepository.save(usuarioEmpresa);
    }
}
