package eterea.tenant.service.brain.usuario.infrastructure.persistence.adapter;

import eterea.tenant.service.brain.usuario.domain.model.UsuarioBrain;
import eterea.tenant.service.brain.usuario.domain.ports.out.UsuarioBrainRepository;
import eterea.tenant.service.brain.usuario.infrastructure.persistence.mapper.UsuarioBrainMapper;
import eterea.tenant.service.brain.usuario.infrastructure.persistence.repository.JpaUsuarioBrainRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaUsuarioBrainRepositoryAdapter implements UsuarioBrainRepository {

    private final JpaUsuarioBrainRepository jpaUsuarioBrainRepository;
    private final UsuarioBrainMapper usuarioBrainMapper;

    @Override
    public List<UsuarioBrain> findAll() {
        return jpaUsuarioBrainRepository.findAll().stream()
                .map(usuarioBrainMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<UsuarioBrain> findById(UUID usuarioId) {
        return jpaUsuarioBrainRepository.findById(usuarioId)
                .map(usuarioBrainMapper::toDomain);
    }

    @Override
    public Optional<UsuarioBrain> findByLogin(String login) {
        return jpaUsuarioBrainRepository.findByLogin(login)
                .map(usuarioBrainMapper::toDomain);
    }

    @Override
    public UsuarioBrain save(UsuarioBrain usuario) {
        return usuarioBrainMapper.toDomain(
                jpaUsuarioBrainRepository.save(usuarioBrainMapper.toEntity(usuario)));
    }
}
