package eterea.tenant.service.modules.personal.legacy.repository.impl;

import eterea.tenant.service.modules.personal.legacy.LegajoRegistro;
import eterea.tenant.service.modules.personal.legacy.repository.LegajoRegistroRepositoryCustom;
import jakarta.persistence.EntityManager;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Consultas de {@code legajoregistro} que necesitan {@code LIMIT 1} y comparar
 * una columna {@code date}/{@code time} con {@link Date}/{@link Time}.
 */
public class LegajoRegistroRepositoryCustomImpl implements LegajoRegistroRepositoryCustom {

    private final EntityManager entityManager;

    public LegajoRegistroRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<LegajoRegistro> findLastByLegajoId(Integer legajoId) {
        List<LegajoRegistro> result = entityManager.createNativeQuery(
                        "SELECT * FROM legajoregistro l "
                                + "WHERE l.lre_leg_id = :legajoId "
                                + "ORDER BY l.lre_fecha DESC, l.lre_hora DESC LIMIT 1",
                        LegajoRegistro.class)
                .setParameter("legajoId", legajoId)
                .getResultList();
        return result.stream().findFirst();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<LegajoRegistro> findLastByLegajoIdAndFecha(
            Integer legajoId, Date fecha, Time hora) {
        List<LegajoRegistro> result = entityManager.createNativeQuery(
                        "SELECT * FROM legajoregistro l "
                                + "WHERE l.lre_leg_id = :legajoId "
                                + "AND l.lre_fecha = :fecha "
                                + "AND l.lre_hora < :hora "
                                + "ORDER BY l.lre_hora DESC LIMIT 1",
                        LegajoRegistro.class)
                .setParameter("legajoId", legajoId)
                .setParameter("fecha", fecha)
                .setParameter("hora", hora)
                .getResultList();
        return result.stream().findFirst();
    }

}
