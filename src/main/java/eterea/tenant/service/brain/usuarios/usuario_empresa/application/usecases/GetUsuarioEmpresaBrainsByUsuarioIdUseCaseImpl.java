package eterea.tenant.service.brain.usuarios.usuario_empresa.application.usecases;

import eterea.tenant.service.brain.usuarios.usuario_empresa.domain.model.UsuarioEmpresaBrain;
import eterea.tenant.service.brain.usuarios.usuario_empresa.domain.ports.in.GetUsuarioEmpresaBrainsByUsuarioIdUseCase;
import eterea.tenant.service.brain.usuarios.usuario_empresa.domain.ports.out.UsuarioEmpresaBrainRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetUsuarioEmpresaBrainsByUsuarioIdUseCaseImpl implements GetUsuarioEmpresaBrainsByUsuarioIdUseCase {

    private final UsuarioEmpresaBrainRepository usuarioEmpresaBrainRepository;

    @Override
    public List<UsuarioEmpresaBrain> getByUsuarioId(UUID usuarioId) {
        return usuarioEmpresaBrainRepository.findByUsuarioId(usuarioId);
    }
}
