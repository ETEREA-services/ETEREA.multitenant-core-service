package eterea.tenant.service.brain.usuarios.usuario_negocio.application.usecases;

import eterea.tenant.service.brain.usuarios.usuario_negocio.application.exception.UsuarioNegocioBrainException;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.model.UsuarioNegocioBrain;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.ports.in.CreateUsuarioNegocioBrainUseCase;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.ports.out.UsuarioNegocioBrainRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUsuarioNegocioBrainUseCaseImpl implements CreateUsuarioNegocioBrainUseCase {

    private final UsuarioNegocioBrainRepository usuarioNegocioBrainRepository;

    @Override
    public UsuarioNegocioBrain create(UsuarioNegocioBrain usuarioNegocio) {
        usuarioNegocioBrainRepository
                .findByUsuarioIdAndNegocioId(usuarioNegocio.getUsuarioId(), usuarioNegocio.getNegocioId())
                .ifPresent(existing -> {
                    throw new UsuarioNegocioBrainException(
                            usuarioNegocio.getUsuarioId(), usuarioNegocio.getNegocioId());
                });
        usuarioNegocio.setUsuarioNegocioId(UUID.randomUUID());
        usuarioNegocio.setActivo(Boolean.TRUE);
        return usuarioNegocioBrainRepository.save(usuarioNegocio);
    }
}
