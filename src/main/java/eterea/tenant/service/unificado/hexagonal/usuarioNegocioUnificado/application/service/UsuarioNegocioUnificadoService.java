package eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.application.service;

import eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.domain.model.UsuarioNegocioUnificado;
import eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.domain.ports.in.FindAllUsuarioNegocioUnificadoUseCase;
import eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.domain.ports.in.FindUsuarioNegocioUnificadoByUsuarioNegocioIdUseCase;
import eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.domain.ports.in.FindUsuarioNegocioUnificadoByUsuarioIdUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioNegocioUnificadoService {

    private final FindAllUsuarioNegocioUnificadoUseCase findAllUseCase;
    private final FindUsuarioNegocioUnificadoByUsuarioNegocioIdUseCase findByUsuarioNegocioIdUseCase;
    private final FindUsuarioNegocioUnificadoByUsuarioIdUseCase findByUsuarioIdUseCase;

    public UsuarioNegocioUnificadoService(FindAllUsuarioNegocioUnificadoUseCase findAllUseCase,
                                          FindUsuarioNegocioUnificadoByUsuarioNegocioIdUseCase findByUsuarioNegocioIdUseCase,
                                          FindUsuarioNegocioUnificadoByUsuarioIdUseCase findByUsuarioIdUseCase) {
        this.findAllUseCase = findAllUseCase;
        this.findByUsuarioNegocioIdUseCase = findByUsuarioNegocioIdUseCase;
        this.findByUsuarioIdUseCase = findByUsuarioIdUseCase;
    }

    public List<UsuarioNegocioUnificado> findAll() {
        return findAllUseCase.findAll();
    }

    public UsuarioNegocioUnificado findByUsuarioNegocioId(Integer usuarioNegocioId) {
        return findByUsuarioNegocioIdUseCase.findByUsuarioNegocioId(usuarioNegocioId);
    }

    public List<UsuarioNegocioUnificado> findByUsuarioId(Long usuarioId) {
        return findByUsuarioIdUseCase.findByUsuarioId(usuarioId);
    }

}
