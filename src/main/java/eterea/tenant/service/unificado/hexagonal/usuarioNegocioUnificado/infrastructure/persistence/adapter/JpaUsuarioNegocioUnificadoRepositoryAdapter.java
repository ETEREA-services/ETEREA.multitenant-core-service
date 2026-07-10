package eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.infrastructure.persistence.adapter;

import eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.domain.model.UsuarioNegocioUnificado;
import eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.domain.ports.out.UsuarioNegocioUnificadoRepository;
import eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.infrastructure.persistence.mapper.UsuarioNegocioUnificadoMapper;
import eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.infrastructure.persistence.repository.JpaUsuarioNegocioUnificadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaUsuarioNegocioUnificadoRepositoryAdapter implements UsuarioNegocioUnificadoRepository {

    private final JpaUsuarioNegocioUnificadoRepository jpaRepository;
    private final UsuarioNegocioUnificadoMapper mapper;

    @Override
    public List<UsuarioNegocioUnificado> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UsuarioNegocioUnificado> findByUsuarioNegocioId(Integer usuarioNegocioId) {
        return jpaRepository.findByUsuarioNegocioId(usuarioNegocioId)
                .map(mapper::toDomain);
    }

    @Override
    public List<UsuarioNegocioUnificado> findByUsuarioId(Long usuarioId) {
        return jpaRepository.findByUsuarioId(usuarioId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

}
