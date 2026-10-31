package eterea.tenant.service.modules.contable.cuenta.infrastructure.persistence.adapter;

import eterea.tenant.service.modules.contable.cuenta.domain.ports.out.NegocioActualPort;
import eterea.tenant.service.modules.platform.api.NegocioActual;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Adapta el contrato de plataforma al puerto del dominio contable.
 */
@Component
@RequiredArgsConstructor
public class JpaNegocioActualAdapter implements NegocioActualPort {

    private final NegocioActual negocioActual;

    @Override
    public Optional<Integer> findNegocioId() {
        return negocioActual.findNegocioId();
    }

}
