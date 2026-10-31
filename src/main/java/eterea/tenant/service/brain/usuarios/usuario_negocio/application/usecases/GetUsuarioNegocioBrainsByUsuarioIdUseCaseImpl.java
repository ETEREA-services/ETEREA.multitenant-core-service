package eterea.tenant.service.brain.usuarios.usuario_negocio.application.usecases;

import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.model.NegocioInfo;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.model.UsuarioNegocioBrain;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.model.UsuarioNegocioConNegocioBrain;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.ports.in.GetUsuarioNegocioBrainsByUsuarioIdUseCase;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.ports.out.NegocioInfoRepository;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.ports.out.UsuarioNegocioBrainRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetUsuarioNegocioBrainsByUsuarioIdUseCaseImpl
        implements GetUsuarioNegocioBrainsByUsuarioIdUseCase {

    private final UsuarioNegocioBrainRepository usuarioNegocioBrainRepository;
    private final NegocioInfoRepository negocioInfoRepository;

    @Override
    public List<UsuarioNegocioConNegocioBrain> getByUsuarioId(UUID usuarioId) {
        return usuarioNegocioBrainRepository.findByUsuarioId(usuarioId).stream()
                .map(asociacion -> negocioInfoRepository.findById(asociacion.getNegocioId())
                        .map(negocio -> toReadModel(asociacion, negocio)))
                .flatMap(Optional::stream)
                .toList();
    }

    private UsuarioNegocioConNegocioBrain toReadModel(
            UsuarioNegocioBrain asociacion,
            NegocioInfo negocio) {
        return UsuarioNegocioConNegocioBrain.builder()
                .usuarioNegocioId(asociacion.getUsuarioNegocioId())
                .usuarioId(asociacion.getUsuarioId())
                .negocioId(asociacion.getNegocioId())
                .activo(asociacion.getActivo())
                .negocio(negocio)
                .build();
    }
}
