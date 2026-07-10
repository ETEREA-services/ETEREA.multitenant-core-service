package eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.application.usecases;

import eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.domain.model.UsuarioNegocioUnificado;
import eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.domain.ports.in.FindAllUsuarioNegocioUnificadoUseCase;
import eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.domain.ports.out.UsuarioNegocioUnificadoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindAllUsuarioNegocioUnificadoUseCaseImpl implements FindAllUsuarioNegocioUnificadoUseCase {

    private final UsuarioNegocioUnificadoRepository repository;

    public FindAllUsuarioNegocioUnificadoUseCaseImpl(UsuarioNegocioUnificadoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UsuarioNegocioUnificado> findAll() {
        return repository.findAll();
    }

}
