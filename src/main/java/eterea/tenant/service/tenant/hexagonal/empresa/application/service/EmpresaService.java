package eterea.tenant.service.tenant.hexagonal.empresa.application.service;

import eterea.tenant.service.tenant.hexagonal.empresa.domain.model.Empresa;
import eterea.tenant.service.tenant.hexagonal.empresa.domain.ports.in.GetLastEmpresaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final GetLastEmpresaUseCase getLastEmpresaUseCase;

    public Optional<Empresa> findLast() {
        return getLastEmpresaUseCase.findLast();
    }

}