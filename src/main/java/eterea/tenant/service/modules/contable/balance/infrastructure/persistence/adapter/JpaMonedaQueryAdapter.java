package eterea.tenant.service.modules.contable.balance.infrastructure.persistence.adapter;

import eterea.tenant.service.modules.contable.balance.domain.model.MonedaInfo;
import eterea.tenant.service.modules.contable.balance.domain.ports.out.MonedaQueryPort;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Catálogo de monedas del tenant.
 *
 * <p>Usa SQL nativo para no importar la entidad legacy de otro módulo. La
 * moneda base es ARS ({@code Mon_ID = 1}).</p>
 */
@Repository
@RequiredArgsConstructor
public class JpaMonedaQueryAdapter implements MonedaQueryPort {

    private static final int MONEDA_BASE = 1;
    private static final String SELECT = """
            SELECT Mon_ID, Mon_Nombre, Mon_Simbolo
            FROM moneda
            """;

    private final EntityManager entityManager;

    @Override
    public List<MonedaInfo> findAll() {
        @SuppressWarnings("unchecked")
        List<Object[]> filas = entityManager
                .createNativeQuery(SELECT + " ORDER BY Mon_ID")
                .getResultList();
        return filas.stream().map(JpaMonedaQueryAdapter::moneda).toList();
    }

    @Override
    public Optional<MonedaInfo> findById(Integer monedaId) {
        if (monedaId == null) {
            return Optional.empty();
        }
        @SuppressWarnings("unchecked")
        List<Object[]> filas = entityManager
                .createNativeQuery(SELECT + " WHERE Mon_ID = :monedaId")
                .setParameter("monedaId", monedaId)
                .getResultList();
        return filas.stream().findFirst().map(JpaMonedaQueryAdapter::moneda);
    }

    private static MonedaInfo moneda(Object[] fila) {
        int id = ((Number) fila[0]).intValue();
        return new MonedaInfo(
                id,
                fila[1] == null ? "" : fila[1].toString(),
                fila[2] == null ? "" : fila[2].toString(),
                id == MONEDA_BASE);
    }

}
