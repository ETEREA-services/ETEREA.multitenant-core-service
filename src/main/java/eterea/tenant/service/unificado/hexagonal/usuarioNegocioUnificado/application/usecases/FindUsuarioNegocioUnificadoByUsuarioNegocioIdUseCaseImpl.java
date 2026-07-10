package eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.application.usecases;

import eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.application.exception.UsuarioNegocioUnificadoException;
import eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.domain.model.UsuarioNegocioUnificado;
import eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.domain.ports.in.FindUsuarioNegocioUnificadoByUsuarioNegocioIdUseCase;
import eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.domain.ports.out.UsuarioNegocioUnificadoRepository;
import org.springframework.stereotype.Component;

@Component
public class FindUsuarioNegocioUnificadoByUsuarioNegocioIdUseCaseImpl implements FindUsuarioNegocioUnificadoByUsuarioNegocioIdUseCase {

    private final UsuarioNegocioUnificadoRepository repository;

    public FindUsuarioNegocioUnificadoByUsuarioNegocioIdUseCaseImpl(UsuarioNegocioUnificadoRepository repository) {
        this.repository = repository;
    }

    @Override
    public UsuarioNegocioUnificado findByUsuarioNegocioId(Integer usuarioNegocioId) {
        return repository.findByUsuarioNegocioId(usuarioNegocioId)
                .orElseThrow(() -> new UsuarioNegocioUnificadoException(usuarioNegocioId));
    }

}
