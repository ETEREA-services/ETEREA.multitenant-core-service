package eterea.tenant.service.brain.usuarios.usuario_negocio.application.service;

import eterea.tenant.service.brain.usuarios.usuario_negocio.application.exception.UsuarioNegocioBrainException;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.model.UsuarioNegocioBrain;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.model.UsuarioNegocioConNegocioBrain;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.ports.in.ActivateUsuarioNegocioBrainUseCase;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.ports.in.CreateUsuarioNegocioBrainUseCase;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.ports.in.DeactivateUsuarioNegocioBrainUseCase;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.ports.in.GetUsuarioNegocioBrainsByUsuarioIdAndEmpresaIdUseCase;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioNegocioBrainService {

    private final CreateUsuarioNegocioBrainUseCase createUsuarioNegocioBrainUseCase;
    private final DeactivateUsuarioNegocioBrainUseCase deactivateUsuarioNegocioBrainUseCase;
    private final ActivateUsuarioNegocioBrainUseCase activateUsuarioNegocioBrainUseCase;
    private final GetUsuarioNegocioBrainsByUsuarioIdAndEmpresaIdUseCase getUsuarioNegocioBrainsByUsuarioIdAndEmpresaIdUseCase;

    public UsuarioNegocioBrain create(UsuarioNegocioBrain usuarioNegocio) {
        return createUsuarioNegocioBrainUseCase.create(usuarioNegocio);
    }

    public UsuarioNegocioBrain deactivate(UUID usuarioNegocioId) {
        return deactivateUsuarioNegocioBrainUseCase.deactivate(usuarioNegocioId)
                .orElseThrow(() -> new UsuarioNegocioBrainException(usuarioNegocioId));
    }

    public UsuarioNegocioBrain activate(UUID usuarioNegocioId) {
        return activateUsuarioNegocioBrainUseCase.activate(usuarioNegocioId)
                .orElseThrow(() -> new UsuarioNegocioBrainException(usuarioNegocioId));
    }

    public List<UsuarioNegocioConNegocioBrain> findAllByUsuarioIdAndEmpresaId(UUID usuarioId, UUID empresaId) {
        return getUsuarioNegocioBrainsByUsuarioIdAndEmpresaIdUseCase.getByUsuarioIdAndEmpresaId(usuarioId, empresaId);
    }
}
