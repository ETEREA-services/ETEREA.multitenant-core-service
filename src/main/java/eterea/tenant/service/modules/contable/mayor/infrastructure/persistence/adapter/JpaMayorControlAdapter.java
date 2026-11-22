package eterea.tenant.service.modules.contable.mayor.infrastructure.persistence.adapter;

import eterea.tenant.service.modules.contable.mayor.domain.model.MayorControl;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorDiscriminadoFiltro;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorFiltro;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorModulo;
import eterea.tenant.service.modules.contable.mayor.domain.ports.out.MayorControlPort;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Controles del Mayor: total, excluidos, módulos y comprobantes sin tipo.
 */
@Repository
@RequiredArgsConstructor
public class JpaMayorControlAdapter implements MayorControlPort {

    private static final String TOTAL = """
            SELECT COUNT(*)
            FROM movcon m
            WHERE m.MCo_Neg_ID = ?
              AND m.Cuenta IN (%s)
              AND m.fecha >= ?
              AND m.fecha < ?
            """;

    private static final String EXCLUIDOS = """
            SELECT COUNT(*)
            FROM movcon m
            LEFT JOIN tiposcomprob t ON t.codigo = m.CgoTComp
            WHERE m.MCo_Neg_ID = ?
              AND m.Cuenta IN (%s)
              AND m.fecha >= ?
              AND m.fecha < ?
              AND t.codigo IS NULL
            """;

    private static final String MODULOS = """
            SELECT mo.Mod_ID, mo.Mod_Nombre, COUNT(*) AS cantidad
            FROM movcon m
            INNER JOIN tiposcomprob t ON t.codigo = m.CgoTComp
            LEFT JOIN modulo mo ON mo.Mod_ID = t.Modulo
            WHERE m.MCo_Neg_ID = ?
              AND m.Cuenta IN (%s)
              AND m.fecha >= ?
              AND m.fecha < ?
            GROUP BY mo.Mod_ID, mo.Mod_Nombre
            ORDER BY cantidad DESC, mo.Mod_Nombre ASC
            """;

    private static final String SIN_TIPO = """
            SELECT DISTINCT m.CgoTComp
            FROM movcon m
            LEFT JOIN tiposcomprob t ON t.codigo = m.CgoTComp
            WHERE m.MCo_Neg_ID = ?
              AND m.Cuenta IN (%s)
              AND m.fecha >= ?
              AND m.fecha < ?
              AND t.codigo IS NULL
            ORDER BY m.CgoTComp
            """;

    private final EntityManager entityManager;

    @Override
    public MayorControl findControl(int negocioId, MayorFiltro filtro) {
        return control(negocioId, List.of(filtro.cuenta()), filtro.desde(), filtro.hasta());
    }

    @Override
    public MayorControl findControlDiscriminado(
            int negocioId, MayorDiscriminadoFiltro filtro) {
        return control(negocioId, filtro.cuentas(), filtro.desde(), filtro.hasta());
    }

    private MayorControl control(
            int negocioId, List<Long> cuentas, LocalDate desde, LocalDate hasta) {
        String in = String.join(",", Collections.nCopies(cuentas.size(), "?"));

        int total = contar(TOTAL.formatted(in), negocioId, cuentas, desde, hasta);
        int excluidos = contar(EXCLUIDOS.formatted(in), negocioId, cuentas, desde, hasta);
        List<MayorModulo> modulos = modulos(MODULOS.formatted(in), negocioId, cuentas, desde, hasta);
        List<Integer> sinTipo = sinTipo(SIN_TIPO.formatted(in), negocioId, cuentas, desde, hasta);

        return new MayorControl(total, excluidos, modulos, sinTipo);
    }

    private int contar(
            String sql, int negocioId, List<Long> cuentas, LocalDate desde, LocalDate hasta) {
        Query query = parametrizar(
                entityManager.createNativeQuery(sql), negocioId, cuentas, desde, hasta);
        Object valor = query.getSingleResult();
        return valor == null ? 0 : ((Number) valor).intValue();
    }

    private List<MayorModulo> modulos(
            String sql, int negocioId, List<Long> cuentas, LocalDate desde, LocalDate hasta) {
        Query query = parametrizar(
                entityManager.createNativeQuery(sql), negocioId, cuentas, desde, hasta);
        @SuppressWarnings("unchecked")
        List<Object[]> filas = query.getResultList();
        List<MayorModulo> modulos = new ArrayList<>(filas.size());
        for (Object[] fila : filas) {
            modulos.add(new MayorModulo(
                    fila[0] == null ? 0 : ((Number) fila[0]).intValue(),
                    fila[1] == null ? "" : fila[1].toString(),
                    ((Number) fila[2]).intValue()));
        }
        return modulos;
    }

    private List<Integer> sinTipo(
            String sql, int negocioId, List<Long> cuentas, LocalDate desde, LocalDate hasta) {
        Query query = parametrizar(
                entityManager.createNativeQuery(sql), negocioId, cuentas, desde, hasta);
        @SuppressWarnings("unchecked")
        List<Object> filas = query.getResultList();
        return filas.stream()
                .map(valor -> valor == null ? 0 : ((Number) valor).intValue())
                .toList();
    }

    private Query parametrizar(
            Query query, int negocioId, List<Long> cuentas, LocalDate desde, LocalDate hasta) {
        int indice = 1;
        query.setParameter(indice++, negocioId);
        for (Long cuenta : cuentas) {
            query.setParameter(indice++, cuenta);
        }
        query.setParameter(indice++, Date.valueOf(desde));
        query.setParameter(indice, Date.valueOf(hasta.plusDays(1)));
        return query;
    }

}
