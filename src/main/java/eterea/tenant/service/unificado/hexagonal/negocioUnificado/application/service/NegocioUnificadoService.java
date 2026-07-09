package eterea.tenant.service.unificado.hexagonal.negocioUnificado.application.service;

import eterea.tenant.service.unificado.hexagonal.negocioUnificado.domain.model.NegocioUnificado;
import eterea.tenant.service.unificado.hexagonal.negocioUnificado.domain.ports.in.FindAllNegocioUnificadoUseCase;
import eterea.tenant.service.unificado.hexagonal.negocioUnificado.domain.ports.in.FindNegocioUnificadoByNegocioIdUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NegocioUnificadoService {

    private final FindAllNegocioUnificadoUseCase findAllNegocioUnificadoUseCase;
    private final FindNegocioUnificadoByNegocioIdUseCase findNegocioUnificadoByNegocioIdUseCase;

    public NegocioUnificadoService(FindAllNegocioUnificadoUseCase findAllNegocioUnificadoUseCase,
                                   FindNegocioUnificadoByNegocioIdUseCase findNegocioUnificadoByNegocioIdUseCase) {
        this.findAllNegocioUnificadoUseCase = findAllNegocioUnificadoUseCase;
        this.findNegocioUnificadoByNegocioIdUseCase = findNegocioUnificadoByNegocioIdUseCase;
    }

    public List<NegocioUnificado> findAll() {
        return findAllNegocioUnificadoUseCase.findAll();
    }

    public NegocioUnificado findByNegocioId(Integer negocioId) {
        return findNegocioUnificadoByNegocioIdUseCase.findByNegocioId(negocioId);
    }

}
