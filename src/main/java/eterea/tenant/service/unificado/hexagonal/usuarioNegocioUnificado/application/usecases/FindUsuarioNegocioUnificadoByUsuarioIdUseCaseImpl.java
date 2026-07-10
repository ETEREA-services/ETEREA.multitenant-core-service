package eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.application.usecases;

import eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.domain.model.UsuarioNegocioUnificado;
import eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.domain.ports.in.FindUsuarioNegocioUnificadoByUsuarioIdUseCase;
import eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.domain.ports.out.UsuarioNegocioUnificadoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindUsuarioNegocioUnificadoByUsuarioIdUseCaseImpl implements FindUsuarioNegocioUnificadoByUsuarioIdUseCase {

    private final UsuarioNegocioUnificadoRepository repository;

    public FindUsuarioNegocioUnificadoByUsuarioIdUseCaseImpl(UsuarioNegocioUnificadoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UsuarioNegocioUnificado> findByUsuarioId(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }

}
