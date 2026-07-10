package eterea.tenant.service.unificado.hexagonal.usuarioUnificado.application.usecases;

import eterea.tenant.service.unificado.hexagonal.usuarioUnificado.application.exception.UsuarioUnificadoException;
import eterea.tenant.service.unificado.hexagonal.usuarioUnificado.domain.model.UsuarioUnificado;
import eterea.tenant.service.unificado.hexagonal.usuarioUnificado.domain.ports.in.FindUsuarioUnificadoByUsuarioIdUseCase;
import eterea.tenant.service.unificado.hexagonal.usuarioUnificado.domain.ports.out.UsuarioUnificadoRepository;
import org.springframework.stereotype.Component;

@Component
public class FindUsuarioUnificadoByUsuarioIdUseCaseImpl implements FindUsuarioUnificadoByUsuarioIdUseCase {

    private final UsuarioUnificadoRepository repository;

    public FindUsuarioUnificadoByUsuarioIdUseCaseImpl(UsuarioUnificadoRepository repository) {
        this.repository = repository;
    }

    @Override
    public UsuarioUnificado findByUsuarioId(Integer usuarioId) {
        return repository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new UsuarioUnificadoException(usuarioId));
    }

}
