package eterea.tenant.service.brain.usuarios.usuario_empresa.application.usecases;

import eterea.tenant.service.brain.negocio.domain.ports.out.NegocioBrainRepository;
import eterea.tenant.service.brain.usuarios.usuario_empresa.domain.model.UsuarioEmpresaBrain;
import eterea.tenant.service.brain.usuarios.usuario_empresa.domain.ports.in.DeactivateUsuarioEmpresaBrainUseCase;
import eterea.tenant.service.brain.usuarios.usuario_empresa.domain.ports.out.UsuarioEmpresaBrainRepository;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.ports.out.UsuarioNegocioBrainRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DeactivateUsuarioEmpresaBrainUseCaseImpl implements DeactivateUsuarioEmpresaBrainUseCase {

    private final UsuarioEmpresaBrainRepository usuarioEmpresaBrainRepository;
    private final UsuarioNegocioBrainRepository usuarioNegocioBrainRepository;
    private final NegocioBrainRepository negocioBrainRepository;

    @Override
    @Transactional
    public Optional<UsuarioEmpresaBrain> deactivate(UUID usuarioEmpresaId) {
        return usuarioEmpresaBrainRepository.findById(usuarioEmpresaId)
                .map(existing -> {
                    existing.setActivo(Boolean.FALSE);
                    UsuarioEmpresaBrain saved = usuarioEmpresaBrainRepository.save(existing);
                    desactivarNegociosDeEmpresa(existing);
                    return saved;
                });
    }

    private void desactivarNegociosDeEmpresa(UsuarioEmpresaBrain usuarioEmpresa) {
        var negocioIds = negocioBrainRepository
                .findAllByEmpresaId(usuarioEmpresa.getEmpresaId()).stream()
                .map(negocio -> negocio.getNegocioId())
                .toList();
        if (negocioIds.isEmpty()) {
            return;
        }
        usuarioNegocioBrainRepository
                .findByUsuarioIdAndNegocioIdIn(usuarioEmpresa.getUsuarioId(), negocioIds)
                .stream()
                .filter(asignacion -> Boolean.TRUE.equals(asignacion.getActivo()))
                .forEach(asignacion -> {
                    asignacion.setActivo(Boolean.FALSE);
                    usuarioNegocioBrainRepository.save(asignacion);
                });
    }
}
