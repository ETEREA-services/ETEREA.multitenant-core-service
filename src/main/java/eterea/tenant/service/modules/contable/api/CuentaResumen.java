package eterea.tenant.service.modules.contable.api;

import eterea.tenant.service.modules.contable.cuenta.infrastructure.persistence.entity.CuentaEntity;
import eterea.tenant.service.modules.contable.cuenta.domain.model.Cuenta;
import java.math.BigDecimal;

/**
 * Datos mínimos de una cuenta contable para otros módulos.
 *
 * <p>Evita que stock, ventas u otros módulos dependan de {@code Cuenta},
 * {@code CuentaEntity} o de los DTOs web del módulo contable.</p>
 */
public record CuentaResumen(
        Long numeroCuenta,
        String nombre,
        Integer negocioId,
        Byte integra,
        Byte transfiere,
        Byte movimientoStock,
        BigDecimal cuentaMaestro,
        Integer grado,
        Long grado1,
        Long grado2,
        Long grado3,
        Long grado4,
        Byte ventas,
        Byte compras,
        Byte gastos) {
}
