package eterea.tenant.service.brain.usuarios.usuario_empresa.application.usecases;

import eterea.tenant.service.brain.empresa.domain.ports.out.EmpresaBrainRepository;
import eterea.tenant.service.brain.usuarios.usuario.domain.ports.out.UsuarioBrainRepository;
import eterea.tenant.service.brain.usuarios.usuario_empresa.application.exception.UsuarioEmpresaBrainConflictException;
import eterea.tenant.service.brain.usuarios.usuario_empresa.application.exception.UsuarioEmpresaBrainNotFoundException;
import eterea.tenant.service.brain.usuarios.usuario_empresa.domain.model.UsuarioEmpresaBrain;
import eterea.tenant.service.brain.usuarios.usuario_empresa.domain.ports.in.CreateUsuarioEmpresaBrainUseCase;
import eterea.tenant.service.brain.usuarios.usuario_empresa.domain.ports.out.UsuarioEmpresaBrainRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CreateUsuarioEmpresaBrainUseCaseImpl implements CreateUsuarioEmpresaBrainUseCase {

    private final UsuarioEmpresaBrainRepository usuarioEmpresaBrainRepository;
    private final UsuarioBrainRepository usuarioBrainRepository;
    private final EmpresaBrainRepository empresaBrainRepository;

    @Override
    @Transactional
    public UsuarioEmpresaBrain create(UsuarioEmpresaBrain usuarioEmpresa) {
        usuarioBrainRepository.findById(usuarioEmpresa.getUsuarioId())
                .orElseThrow(() -> new UsuarioEmpresaBrainNotFoundException(
                        "No existe el usuario con id: " + usuarioEmpresa.getUsuarioId()));
        empresaBrainRepository.findById(usuarioEmpresa.getEmpresaId())
                .orElseThrow(() -> new UsuarioEmpresaBrainNotFoundException(
                        "No existe la empresa con id: " + usuarioEmpresa.getEmpresaId()));

        return usuarioEmpresaBrainRepository
                .findByUsuarioIdAndEmpresaId(
                        usuarioEmpresa.getUsuarioId(), usuarioEmpresa.getEmpresaId())
                .map(existing -> reactivar(existing))
                .orElseGet(() -> crear(usuarioEmpresa));
    }

    private UsuarioEmpresaBrain reactivar(UsuarioEmpresaBrain existing) {
        if (Boolean.TRUE.equals(existing.getActivo())) {
            throw new UsuarioEmpresaBrainConflictException(
                    "La empresa ya está asignada al usuario");
        }
        existing.setActivo(Boolean.TRUE);
        return usuarioEmpresaBrainRepository.save(existing);
    }

    private UsuarioEmpresaBrain crear(UsuarioEmpresaBrain usuarioEmpresa) {
        usuarioEmpresa.setUsuarioEmpresaId(UUID.randomUUID());
        usuarioEmpresa.setActivo(Boolean.TRUE);
        return usuarioEmpresaBrainRepository.save(usuarioEmpresa);
    }
}
