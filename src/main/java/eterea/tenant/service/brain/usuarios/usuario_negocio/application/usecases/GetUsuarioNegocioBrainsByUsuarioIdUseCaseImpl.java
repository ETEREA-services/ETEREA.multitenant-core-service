package eterea.tenant.service.brain.usuarios.usuario_negocio.application.usecases;

import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.model.NegocioInfo;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.model.UsuarioNegocioBrain;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.model.UsuarioNegocioConNegocioBrain;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.ports.in.GetUsuarioNegocioBrainsByUsuarioIdUseCase;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.ports.out.NegocioInfoRepository;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.ports.out.UsuarioNegocioBrainRepository;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
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
        List<UsuarioNegocioBrain> asociaciones = usuarioNegocioBrainRepository.findByUsuarioId(usuarioId);
        if (asociaciones.isEmpty()) {
            return List.of();
        }

        /*
         * Una sola consulta de negocios por todos los ids asignados: antes se
         * hacía un findById por asociación.
         */
        Map<Integer, NegocioInfo> negocios = negocioInfoRepository.findAllByIds(
                asociaciones.stream().map(UsuarioNegocioBrain::getNegocioId).toList())
                .stream()
                .collect(Collectors.toMap(NegocioInfo::getNegocioId, Function.identity()));

        return asociaciones.stream()
                .filter(asociacion -> negocios.containsKey(asociacion.getNegocioId()))
                .map(asociacion -> toReadModel(asociacion, negocios.get(asociacion.getNegocioId())))
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
