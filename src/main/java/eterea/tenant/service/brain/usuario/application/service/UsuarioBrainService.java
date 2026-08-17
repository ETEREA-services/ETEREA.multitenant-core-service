package eterea.tenant.service.brain.usuario.application.service;

import eterea.tenant.service.brain.usuario.application.exception.UsuarioBrainException;
import eterea.tenant.service.brain.usuario.domain.model.UsuarioBrain;
import eterea.tenant.service.brain.usuario.domain.ports.in.ChangeUsuarioBrainPasswordUseCase;
import eterea.tenant.service.brain.usuario.domain.ports.in.CreateUsuarioBrainUseCase;
import eterea.tenant.service.brain.usuario.domain.ports.in.GetAllUsuarioBrainsUseCase;
import eterea.tenant.service.brain.usuario.domain.ports.in.GetUsuarioBrainByIdUseCase;
import eterea.tenant.service.brain.usuario.domain.ports.in.LoginUsuarioBrainUseCase;
import eterea.tenant.service.brain.usuario.domain.ports.in.UpdateUsuarioBrainUseCase;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioBrainService {

    private final GetAllUsuarioBrainsUseCase getAllUsuarioBrainsUseCase;
    private final GetUsuarioBrainByIdUseCase getUsuarioBrainByIdUseCase;
    private final CreateUsuarioBrainUseCase createUsuarioBrainUseCase;
    private final UpdateUsuarioBrainUseCase updateUsuarioBrainUseCase;
    private final LoginUsuarioBrainUseCase loginUsuarioBrainUseCase;
    private final ChangeUsuarioBrainPasswordUseCase changeUsuarioBrainPasswordUseCase;

    public List<UsuarioBrain> findAll() {
        return getAllUsuarioBrainsUseCase.getAll();
    }

    public UsuarioBrain findById(UUID usuarioId) {
        return getUsuarioBrainByIdUseCase.getById(usuarioId)
                .orElseThrow(() -> new UsuarioBrainException(usuarioId));
    }

    public UsuarioBrain create(UsuarioBrain usuario, String password) {
        return createUsuarioBrainUseCase.create(usuario, password);
    }

    public UsuarioBrain update(UUID usuarioId, UsuarioBrain usuario, String password) {
        return updateUsuarioBrainUseCase.update(usuarioId, usuario, password)
                .orElseThrow(() -> new UsuarioBrainException(usuarioId));
    }

    public UsuarioBrain login(String login, String password, String ip) {
        return loginUsuarioBrainUseCase.login(login, password, ip)
                .orElseThrow(() -> new UsuarioBrainException("Credenciales inválidas"));
    }

    public void changePassword(UUID usuarioId, String currentPassword, String newPassword) {
        if (!changeUsuarioBrainPasswordUseCase.change(usuarioId, currentPassword, newPassword)) {
            throw new UsuarioBrainException("La password actual es inválida o el usuario no existe");
        }
    }
}
