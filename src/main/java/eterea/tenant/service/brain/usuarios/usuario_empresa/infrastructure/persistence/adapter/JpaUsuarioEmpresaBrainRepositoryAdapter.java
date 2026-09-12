package eterea.tenant.service.brain.usuarios.usuario_empresa.infrastructure.persistence.adapter;

import eterea.tenant.service.brain.usuarios.usuario_empresa.domain.model.UsuarioEmpresaBrain;
import eterea.tenant.service.brain.usuarios.usuario_empresa.domain.ports.out.UsuarioEmpresaBrainRepository;
import eterea.tenant.service.brain.usuarios.usuario_empresa.infrastructure.persistence.mapper.UsuarioEmpresaBrainMapper;
import eterea.tenant.service.brain.usuarios.usuario_empresa.infrastructure.persistence.repository.JpaUsuarioEmpresaBrainRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaUsuarioEmpresaBrainRepositoryAdapter implements UsuarioEmpresaBrainRepository {

    private final JpaUsuarioEmpresaBrainRepository jpaUsuarioEmpresaBrainRepository;
    private final UsuarioEmpresaBrainMapper usuarioEmpresaBrainMapper;

    @Override
    public List<UsuarioEmpresaBrain> findByUsuarioId(UUID usuarioId) {
        return jpaUsuarioEmpresaBrainRepository.findByUsuarioId(usuarioId).stream()
                .map(usuarioEmpresaBrainMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<UsuarioEmpresaBrain> findById(UUID usuarioEmpresaId) {
        return jpaUsuarioEmpresaBrainRepository.findById(usuarioEmpresaId)
                .map(usuarioEmpresaBrainMapper::toDomain);
    }

    @Override
    public UsuarioEmpresaBrain save(UsuarioEmpresaBrain usuarioEmpresa) {
        return usuarioEmpresaBrainMapper.toDomain(
                jpaUsuarioEmpresaBrainRepository.save(usuarioEmpresaBrainMapper.toEntity(usuarioEmpresa)));
    }
}
