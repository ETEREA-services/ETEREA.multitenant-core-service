package eterea.tenant.service.brain.usuarios.usuario_negocio.application.usecases;

import eterea.tenant.service.brain.negocio.domain.ports.out.NegocioBrainRepository;
import eterea.tenant.service.brain.usuarios.usuario.domain.ports.out.UsuarioBrainRepository;
import eterea.tenant.service.brain.usuarios.usuario_empresa.domain.ports.out.UsuarioEmpresaBrainRepository;
import eterea.tenant.service.brain.usuarios.usuario_negocio.application.exception.UsuarioNegocioBrainConflictException;
import eterea.tenant.service.brain.usuarios.usuario_negocio.application.exception.UsuarioNegocioBrainNotFoundException;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.model.UsuarioNegocioBrain;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.ports.in.CreateUsuarioNegocioBrainUseCase;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.ports.out.UsuarioNegocioBrainRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CreateUsuarioNegocioBrainUseCaseImpl implements CreateUsuarioNegocioBrainUseCase {

    private final UsuarioNegocioBrainRepository usuarioNegocioBrainRepository;
    private final UsuarioBrainRepository usuarioBrainRepository;
    private final NegocioBrainRepository negocioBrainRepository;
    private final UsuarioEmpresaBrainRepository usuarioEmpresaBrainRepository;

    @Override
    @Transactional
    public UsuarioNegocioBrain create(UsuarioNegocioBrain usuarioNegocio) {
        usuarioBrainRepository.findById(usuarioNegocio.getUsuarioId())
                .orElseThrow(() -> new UsuarioNegocioBrainNotFoundException(
                        "No existe el usuario con id: " + usuarioNegocio.getUsuarioId()));

        var negocio = negocioBrainRepository.findById(usuarioNegocio.getNegocioId())
                .orElseThrow(() -> new UsuarioNegocioBrainNotFoundException(
                        "No existe el negocio con id: " + usuarioNegocio.getNegocioId()));

        usuarioEmpresaBrainRepository
                .findByUsuarioIdAndEmpresaId(usuarioNegocio.getUsuarioId(), negocio.getEmpresaId())
                .filter(asignacion -> Boolean.TRUE.equals(asignacion.getActivo()))
                .orElseThrow(() -> new UsuarioNegocioBrainConflictException(
                        "Primero asigná la empresa del negocio al usuario"));

        return usuarioNegocioBrainRepository
                .findByUsuarioIdAndNegocioId(
                        usuarioNegocio.getUsuarioId(), usuarioNegocio.getNegocioId())
                .map(existing -> reactivar(existing))
                .orElseGet(() -> crear(usuarioNegocio));
    }

    private UsuarioNegocioBrain reactivar(UsuarioNegocioBrain existing) {
        if (Boolean.TRUE.equals(existing.getActivo())) {
            throw new UsuarioNegocioBrainConflictException(
                    "El negocio ya está asignado al usuario");
        }
        existing.setActivo(Boolean.TRUE);
        return usuarioNegocioBrainRepository.save(existing);
    }

    private UsuarioNegocioBrain crear(UsuarioNegocioBrain usuarioNegocio) {
        usuarioNegocio.setUsuarioNegocioId(UUID.randomUUID());
        usuarioNegocio.setActivo(Boolean.TRUE);
        return usuarioNegocioBrainRepository.save(usuarioNegocio);
    }
}
