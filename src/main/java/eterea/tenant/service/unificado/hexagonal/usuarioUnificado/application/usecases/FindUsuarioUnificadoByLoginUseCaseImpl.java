package eterea.tenant.service.unificado.hexagonal.usuarioUnificado.application.usecases;

import eterea.tenant.service.unificado.hexagonal.usuarioUnificado.application.exception.UsuarioUnificadoException;
import eterea.tenant.service.unificado.hexagonal.usuarioUnificado.domain.model.UsuarioUnificado;
import eterea.tenant.service.unificado.hexagonal.usuarioUnificado.domain.ports.in.FindUsuarioUnificadoByLoginUseCase;
import eterea.tenant.service.unificado.hexagonal.usuarioUnificado.domain.ports.out.UsuarioUnificadoRepository;
import org.springframework.stereotype.Component;

@Component
public class FindUsuarioUnificadoByLoginUseCaseImpl implements FindUsuarioUnificadoByLoginUseCase {

    private final UsuarioUnificadoRepository repository;

    public FindUsuarioUnificadoByLoginUseCaseImpl(UsuarioUnificadoRepository repository) {
        this.repository = repository;
    }

    @Override
    public UsuarioUnificado findByLogin(String login) {
        return repository.findByLogin(login)
                .orElseThrow(() -> new UsuarioUnificadoException(login));
    }

}
