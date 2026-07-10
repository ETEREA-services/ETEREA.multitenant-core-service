package eterea.tenant.service.unificado.hexagonal.usuarioUnificado.application.usecases;

import eterea.tenant.service.unificado.hexagonal.usuarioUnificado.domain.model.UsuarioUnificado;
import eterea.tenant.service.unificado.hexagonal.usuarioUnificado.domain.ports.in.FindAllUsuarioUnificadoUseCase;
import eterea.tenant.service.unificado.hexagonal.usuarioUnificado.domain.ports.out.UsuarioUnificadoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindAllUsuarioUnificadoUseCaseImpl implements FindAllUsuarioUnificadoUseCase {

    private final UsuarioUnificadoRepository repository;

    public FindAllUsuarioUnificadoUseCaseImpl(UsuarioUnificadoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UsuarioUnificado> findAll() {
        return repository.findAll();
    }

}
