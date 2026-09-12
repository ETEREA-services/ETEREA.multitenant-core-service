package eterea.tenant.service.brain.usuarios.usuario_empresa.application.service;

import eterea.tenant.service.brain.usuarios.usuario_empresa.application.exception.UsuarioEmpresaBrainException;
import eterea.tenant.service.brain.usuarios.usuario_empresa.domain.model.UsuarioEmpresaBrain;
import eterea.tenant.service.brain.usuarios.usuario_empresa.domain.ports.in.ActivateUsuarioEmpresaBrainUseCase;
import eterea.tenant.service.brain.usuarios.usuario_empresa.domain.ports.in.CreateUsuarioEmpresaBrainUseCase;
import eterea.tenant.service.brain.usuarios.usuario_empresa.domain.ports.in.DeactivateUsuarioEmpresaBrainUseCase;
import eterea.tenant.service.brain.usuarios.usuario_empresa.domain.ports.in.GetUsuarioEmpresaBrainsByUsuarioIdUseCase;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioEmpresaBrainService {

    private final CreateUsuarioEmpresaBrainUseCase createUsuarioEmpresaBrainUseCase;
    private final DeactivateUsuarioEmpresaBrainUseCase deactivateUsuarioEmpresaBrainUseCase;
    private final ActivateUsuarioEmpresaBrainUseCase activateUsuarioEmpresaBrainUseCase;
    private final GetUsuarioEmpresaBrainsByUsuarioIdUseCase getUsuarioEmpresaBrainsByUsuarioIdUseCase;

    public UsuarioEmpresaBrain create(UsuarioEmpresaBrain usuarioEmpresa) {
        return createUsuarioEmpresaBrainUseCase.create(usuarioEmpresa);
    }

    public UsuarioEmpresaBrain deactivate(UUID usuarioEmpresaId) {
        return deactivateUsuarioEmpresaBrainUseCase.deactivate(usuarioEmpresaId)
                .orElseThrow(() -> new UsuarioEmpresaBrainException(usuarioEmpresaId));
    }

    public UsuarioEmpresaBrain activate(UUID usuarioEmpresaId) {
        return activateUsuarioEmpresaBrainUseCase.activate(usuarioEmpresaId)
                .orElseThrow(() -> new UsuarioEmpresaBrainException(usuarioEmpresaId));
    }

    public List<UsuarioEmpresaBrain> findAllByUsuarioId(UUID usuarioId) {
        return getUsuarioEmpresaBrainsByUsuarioIdUseCase.getByUsuarioId(usuarioId);
    }
}
