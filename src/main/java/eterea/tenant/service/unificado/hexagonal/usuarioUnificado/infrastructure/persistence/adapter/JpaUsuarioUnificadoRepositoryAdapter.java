package eterea.tenant.service.unificado.hexagonal.usuarioUnificado.infrastructure.persistence.adapter;

import eterea.tenant.service.unificado.hexagonal.usuarioUnificado.domain.model.UsuarioUnificado;
import eterea.tenant.service.unificado.hexagonal.usuarioUnificado.domain.ports.out.UsuarioUnificadoRepository;
import eterea.tenant.service.unificado.hexagonal.usuarioUnificado.infrastructure.persistence.mapper.UsuarioUnificadoMapper;
import eterea.tenant.service.unificado.hexagonal.usuarioUnificado.infrastructure.persistence.repository.JpaUsuarioUnificadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaUsuarioUnificadoRepositoryAdapter implements UsuarioUnificadoRepository {

    private final JpaUsuarioUnificadoRepository jpaRepository;
    private final UsuarioUnificadoMapper mapper;

    @Override
    public List<UsuarioUnificado> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UsuarioUnificado> findByUsuarioId(Integer usuarioId) {
        return jpaRepository.findByUsuarioId(usuarioId)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<UsuarioUnificado> findByLogin(String login) {
        return jpaRepository.findByLogin(login)
                .map(mapper::toDomain);
    }

}
