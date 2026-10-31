package eterea.tenant.service.modules.compras.legacy.hexagonal.proveedormovimiento.infrastructure.persistence.repository.impl;

import eterea.tenant.service.modules.compras.legacy.hexagonal.proveedormovimiento.infrastructure.persistence.dto.ResumenIvaComprasMensualDto;
import eterea.tenant.service.modules.compras.legacy.hexagonal.proveedormovimiento.infrastructure.persistence.dto.ResumenIvaComprasMensualPosicionDto;
import eterea.tenant.service.modules.compras.legacy.hexagonal.proveedormovimiento.infrastructure.persistence.repository.JpaProveedorMovimientoRepositoryCustom;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

public class JpaProveedorMovimientoRepositoryCustomImpl
        implements JpaProveedorMovimientoRepositoryCustom {

    private static final String RESUMEN_MENSUAL = """
            SELECT
                movprov.`MPr_Neg_ID` AS negocio_id,
                YEAR(`movprov`.`FechaReg`) AS `anho`,
                MONTH(`movprov`.`FechaReg`) AS `mes`,
                SUM(`movprov`.`Neto`) AS `neto`,
                SUM(`movprov`.`MontoFactC`) AS facturado_c,
                SUM(movprov.`GNG`) AS gastos_no_gravados,
                SUM(`movprov`.`MontoIva`) AS `iva21`,
                SUM(`movprov`.`MontoIva27`) AS `iva27`,
                SUM(`movprov`.`MontoIva105`) AS `iva105`,
                SUM(`movprov`.`PercIva`) AS `percepcion_iva`,
                SUM(`movprov`.`PercIngBrutos`) AS `percepcion_ingresos_brutos`,
                SUM(`movprov`.`Neto` + `movprov`.`MontoFactC` + `movprov`.`GNG` + `movprov`.`MontoIva`
                    + `movprov`.`MontoIva27` + `movprov`.`MontoIva105` + `movprov`.`PercIva`
                    + `movprov`.`PercIngBrutos`) AS total_calculado,
                SUM(`movprov`.`Importe`) AS `total`
            FROM `movprov`
            JOIN `tiposcomprob`
            ON `movprov`.`CgoComprob` = `tiposcomprob`.`codigo`
            WHERE (`tiposcomprob`.`LibroIva` <> 0)
            AND YEAR(`movprov`.`FechaReg`) = :year
            AND MONTH(`movprov`.`FechaReg`) = :month
            GROUP BY `movprov`.`MPr_Neg_ID`, YEAR(`movprov`.`FechaReg`), MONTH(`movprov`.`FechaReg`)
            """;

    private static final String RESUMEN_MENSUAL_POSICION = """
            SELECT
                movprov.`MPr_Neg_ID` AS negocio_id,
                YEAR(`movprov`.`FechaReg`) AS `anho`,
                MONTH(`movprov`.`FechaReg`) AS `mes`,
                `proveedores`.`Posicion` AS `posicion`,
                SUM(`movprov`.`Neto`) AS `neto`,
                SUM(`movprov`.`MontoFactC`) AS facturado_c,
                SUM(`movprov`.`GNG`) AS `gastos_no_gravados`,
                SUM(`movprov`.`MontoIva`) AS `iva21`,
                SUM(`movprov`.`MontoIva27`) AS `iva27`,
                SUM(`movprov`.`MontoIva105`) AS `iva105`,
                SUM(`movprov`.`PercIva`) AS `percepcion_iva`,
                SUM(`movprov`.`PercIngBrutos`) AS `percepcion_ingresos_brutos`,
                SUM(`movprov`.`Importe`) AS `total`
            FROM `movprov`
            JOIN `proveedores`
            ON `movprov`.`CgoProv` = `proveedores`.`codigo`
            JOIN `tiposcomprob`
            ON `movprov`.`CgoComprob` = `tiposcomprob`.`codigo`
            WHERE `tiposcomprob`.`LibroIva` <> 0
            AND YEAR(movprov.`FechaReg`) = :year
            AND MONTH(movprov.`FechaReg`) = :month
            GROUP BY `movprov`.`MPr_Neg_ID`, YEAR(`movprov`.`FechaReg`),
                MONTH(`movprov`.`FechaReg`), `proveedores`.`Posicion`
            """;

    private final EntityManager entityManager;

    public JpaProveedorMovimientoRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ResumenIvaComprasMensualDto findResumenByYearAndMonth(Integer year, Integer month) {
        List<Object[]> rows = entityManager.createNativeQuery(RESUMEN_MENSUAL)
                .setParameter("year", year)
                .setParameter("month", month)
                .getResultList();
        if (rows.isEmpty()) {
            return null;
        }
        return toResumen(rows.getFirst());
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ResumenIvaComprasMensualPosicionDto> findAllResumenPosicionByYearAndMonth(
            Integer year, Integer month) {
        List<Object[]> rows = entityManager.createNativeQuery(RESUMEN_MENSUAL_POSICION)
                .setParameter("year", year)
                .setParameter("month", month)
                .getResultList();
        return rows.stream().map(this::toResumenPosicion).toList();
    }

    private ResumenIvaComprasMensualDto toResumen(Object[] row) {
        return new ResumenIvaComprasMensualRow(
                toInteger(row[0]),
                toInteger(row[1]),
                toInteger(row[2]),
                toBigDecimal(row[3]),
                toBigDecimal(row[4]),
                toBigDecimal(row[5]),
                toBigDecimal(row[6]),
                toBigDecimal(row[7]),
                toBigDecimal(row[8]),
                toBigDecimal(row[9]),
                toBigDecimal(row[10]),
                toBigDecimal(row[11]),
                toBigDecimal(row[12]));
    }

    private ResumenIvaComprasMensualPosicionDto toResumenPosicion(Object[] row) {
        return new ResumenIvaComprasMensualPosicionRow(
                toInteger(row[0]),
                toInteger(row[1]),
                toInteger(row[2]),
                toInteger(row[3]),
                toBigDecimal(row[4]),
                toBigDecimal(row[5]),
                toBigDecimal(row[6]),
                toBigDecimal(row[7]),
                toBigDecimal(row[8]),
                toBigDecimal(row[9]),
                toBigDecimal(row[10]),
                toBigDecimal(row[11]),
                toBigDecimal(row[12]));
    }

    private static Integer toInteger(Object value) {
        return value == null ? null : ((Number) value).intValue();
    }

    private static BigDecimal toBigDecimal(Object value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        if (value instanceof BigDecimal decimal) {
            return decimal;
        }
        return new BigDecimal(value.toString());
    }

    @Getter
    @AllArgsConstructor
    private static final class ResumenIvaComprasMensualRow implements ResumenIvaComprasMensualDto {

        private final Integer negocioId;
        private final Integer anho;
        private final Integer mes;
        private final BigDecimal neto;
        private final BigDecimal facturadoC;
        private final BigDecimal gastosNoGravados;
        private final BigDecimal iva21;
        private final BigDecimal iva27;
        private final BigDecimal iva105;
        private final BigDecimal percepcionIva;
        private final BigDecimal percepcionIngresosBrutos;
        private final BigDecimal totalCalculado;
        private final BigDecimal total;

    }

    @Getter
    @AllArgsConstructor
    private static final class ResumenIvaComprasMensualPosicionRow
            implements ResumenIvaComprasMensualPosicionDto {

        private final Integer negocioId;
        private final Integer anho;
        private final Integer mes;
        private final Integer posicion;
        private final BigDecimal neto;
        private final BigDecimal facturadoC;
        private final BigDecimal gastosNoGravados;
        private final BigDecimal iva21;
        private final BigDecimal iva27;
        private final BigDecimal iva105;
        private final BigDecimal percepcionIva;
        private final BigDecimal percepcionIngresosBrutos;
        private final BigDecimal total;

    }


    @Override
    public List<eterea.tenant.service.modules.compras.legacy.hexagonal.proveedormovimiento.infrastructure.persistence.entity.ProveedorMovimientoEntity>
            findAllByFechaContableBetweenAndComprobanteLibroIva(
                    java.time.OffsetDateTime desde,
                    java.time.OffsetDateTime hasta,
                    Byte libroIva,
                    org.springframework.data.domain.Sort sort) {
        String sql = """
                SELECT movprov.*
                FROM movprov
                JOIN tiposcomprob ON movprov.CgoComprob = tiposcomprob.codigo
                WHERE movprov.fechareg BETWEEN :desde AND :hasta
                  AND tiposcomprob.LibroIva = :libroIva
                ORDER BY movprov.fechacomprob, movprov.prefijo, movprov.nrocomprob
                """;
        return entityManager.createNativeQuery(sql,
                        eterea.tenant.service.modules.compras.legacy.hexagonal.proveedormovimiento.infrastructure.persistence.entity.ProveedorMovimientoEntity.class)
                .setParameter("desde", desde)
                .setParameter("hasta", hasta)
                .setParameter("libroIva", libroIva)
                .getResultList();
    }
}
