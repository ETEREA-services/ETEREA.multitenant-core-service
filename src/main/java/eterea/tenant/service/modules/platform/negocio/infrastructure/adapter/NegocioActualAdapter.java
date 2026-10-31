package eterea.tenant.service.modules.platform.negocio.infrastructure.adapter;

import eterea.tenant.service.configuration.TenantContext;
import eterea.tenant.service.modules.platform.api.NegocioActual;
import eterea.tenant.service.modules.platform.api.NegocioActualInfo;
import eterea.tenant.service.modules.platform.negocio.domain.model.Negocio;
import eterea.tenant.service.modules.platform.negocio.domain.ports.out.NegocioRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Adaptador del contrato {@link NegocioActual}.
 *
 * <p>La base del tenant se resuelve con {@code TenantContext} y la fila de
 * {@code negocio} de esa base.</p>
 */
@Component
@RequiredArgsConstructor
public class NegocioActualAdapter implements NegocioActual {

    private final NegocioRepository negocioRepository;

    @Override
    public Optional<Integer> findNegocioId() {
        return findActual().map(NegocioActualInfo::negocioId);
    }

    @Override
    public Optional<NegocioActualInfo> findActual() {
        return Optional.ofNullable(TenantContext.getCurrentDatabaseName())
                .flatMap(negocioRepository::findByDatabase)
                .map(NegocioActualAdapter::toInfo);
    }

    private static NegocioActualInfo toInfo(Negocio negocio) {
        return new NegocioActualInfo(negocio.getNegocioId(), negocio.getNombre());
    }

}
