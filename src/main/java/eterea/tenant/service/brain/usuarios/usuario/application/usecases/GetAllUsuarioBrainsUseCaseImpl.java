package eterea.tenant.service.brain.usuarios.usuario.application.usecases;

import eterea.tenant.service.brain.usuarios.usuario.domain.model.UsuarioBrain;
import eterea.tenant.service.brain.usuarios.usuario.domain.ports.in.GetAllUsuarioBrainsUseCase;
import eterea.tenant.service.brain.usuarios.usuario.domain.ports.out.UsuarioBrainRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetAllUsuarioBrainsUseCaseImpl implements GetAllUsuarioBrainsUseCase {

    private final UsuarioBrainRepository usuarioBrainRepository;

    @Override
    public List<UsuarioBrain> getAll() {
        return usuarioBrainRepository.findAll();
    }
}
