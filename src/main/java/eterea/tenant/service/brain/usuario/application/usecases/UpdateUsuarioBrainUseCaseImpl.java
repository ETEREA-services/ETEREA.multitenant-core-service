package eterea.tenant.service.brain.usuario.application.usecases;

import eterea.tenant.service.brain.usuario.domain.model.UsuarioBrain;
import eterea.tenant.service.brain.usuario.domain.ports.in.UpdateUsuarioBrainUseCase;
import eterea.tenant.service.brain.usuario.domain.ports.out.PasswordHashPort;
import eterea.tenant.service.brain.usuario.domain.ports.out.UsuarioBrainRepository;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateUsuarioBrainUseCaseImpl implements UpdateUsuarioBrainUseCase {

    private final UsuarioBrainRepository usuarioBrainRepository;
    private final PasswordHashPort passwordHashPort;

    @Override
    public Optional<UsuarioBrain> update(UUID usuarioId, UsuarioBrain changes, String password) {
        return usuarioBrainRepository.findById(usuarioId).map(current -> {
            current.setLogin(changes.getLogin());
            current.setNombre(changes.getNombre());
            current.setEmail(changes.getEmail());
            if (changes.getActivo() != null) {
                current.setActivo(changes.getActivo());
            }
            if (password != null && !password.isBlank()) {
                current.setPasswordHash(passwordHashPort.hash(password));
                current.setPasswordChangedAt(OffsetDateTime.now());
            }
            return usuarioBrainRepository.save(current);
        });
    }
}
