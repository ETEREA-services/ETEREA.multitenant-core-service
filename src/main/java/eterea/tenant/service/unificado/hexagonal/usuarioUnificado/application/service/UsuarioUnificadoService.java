package eterea.tenant.service.unificado.hexagonal.usuarioUnificado.application.service;

import eterea.tenant.service.unificado.hexagonal.usuarioUnificado.domain.model.UsuarioUnificado;
import eterea.tenant.service.unificado.hexagonal.usuarioUnificado.domain.ports.in.FindAllUsuarioUnificadoUseCase;
import eterea.tenant.service.unificado.hexagonal.usuarioUnificado.domain.ports.in.FindUsuarioUnificadoByLoginUseCase;
import eterea.tenant.service.unificado.hexagonal.usuarioUnificado.domain.ports.in.FindUsuarioUnificadoByUsuarioIdUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioUnificadoService {

    private final FindAllUsuarioUnificadoUseCase findAllUsuarioUnificadoUseCase;
    private final FindUsuarioUnificadoByUsuarioIdUseCase findUsuarioUnificadoByUsuarioIdUseCase;
    private final FindUsuarioUnificadoByLoginUseCase findUsuarioUnificadoByLoginUseCase;

    public UsuarioUnificadoService(FindAllUsuarioUnificadoUseCase findAllUsuarioUnificadoUseCase,
                                   FindUsuarioUnificadoByUsuarioIdUseCase findUsuarioUnificadoByUsuarioIdUseCase,
                                   FindUsuarioUnificadoByLoginUseCase findUsuarioUnificadoByLoginUseCase) {
        this.findAllUsuarioUnificadoUseCase = findAllUsuarioUnificadoUseCase;
        this.findUsuarioUnificadoByUsuarioIdUseCase = findUsuarioUnificadoByUsuarioIdUseCase;
        this.findUsuarioUnificadoByLoginUseCase = findUsuarioUnificadoByLoginUseCase;
    }

    public List<UsuarioUnificado> findAll() {
        return findAllUsuarioUnificadoUseCase.findAll();
    }

    public UsuarioUnificado findByUsuarioId(Integer usuarioId) {
        return findUsuarioUnificadoByUsuarioIdUseCase.findByUsuarioId(usuarioId);
    }

    public UsuarioUnificado findByLogin(String login) {
        return findUsuarioUnificadoByLoginUseCase.findByLogin(login);
    }

}
