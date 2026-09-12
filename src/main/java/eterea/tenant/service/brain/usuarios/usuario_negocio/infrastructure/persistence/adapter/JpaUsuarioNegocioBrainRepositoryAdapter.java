package eterea.tenant.service.brain.usuarios.usuario_negocio.infrastructure.persistence.adapter;

import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.model.UsuarioNegocioBrain;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.ports.out.UsuarioNegocioBrainRepository;
import eterea.tenant.service.brain.usuarios.usuario_negocio.infrastructure.persistence.mapper.UsuarioNegocioBrainMapper;
import eterea.tenant.service.brain.usuarios.usuario_negocio.infrastructure.persistence.repository.JpaUsuarioNegocioBrainRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaUsuarioNegocioBrainRepositoryAdapter implements UsuarioNegocioBrainRepository {

    private final JpaUsuarioNegocioBrainRepository jpaUsuarioNegocioBrainRepository;
    private final UsuarioNegocioBrainMapper usuarioNegocioBrainMapper;

    @Override
    public List<UsuarioNegocioBrain> findByUsuarioId(UUID usuarioId) {
        return jpaUsuarioNegocioBrainRepository.findByUsuarioId(usuarioId).stream()
                .map(usuarioNegocioBrainMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<UsuarioNegocioBrain> findById(UUID usuarioNegocioId) {
        return jpaUsuarioNegocioBrainRepository.findById(usuarioNegocioId)
                .map(usuarioNegocioBrainMapper::toDomain);
    }

    @Override
    public Optional<UsuarioNegocioBrain> findByUsuarioIdAndNegocioId(UUID usuarioId, Integer negocioId) {
        return jpaUsuarioNegocioBrainRepository.findByUsuarioIdAndNegocioId(usuarioId, negocioId)
                .map(usuarioNegocioBrainMapper::toDomain);
    }

    @Override
    public UsuarioNegocioBrain save(UsuarioNegocioBrain usuarioNegocio) {
        return usuarioNegocioBrainMapper.toDomain(
                jpaUsuarioNegocioBrainRepository.save(usuarioNegocioBrainMapper.toEntity(usuarioNegocio)));
    }
}
