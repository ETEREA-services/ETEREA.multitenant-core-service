package eterea.tenant.service.modules.contable.mayor.domain.model;

import java.util.List;

/**
 * Controles operativos que acompañan al Mayor.
 *
 * @param totalMovimientos    filas de {@code movcon} en el rango, incluyendo
 *                            las que se excluyen por no tener tipo
 * @param movimientosExcluidos subconjunto sin {@code tiposcomprob} válido (D9-C)
 * @param modulos              agrupación de las filas con tipo válido
 * @param comprobantesSinTipo  códigos distintos sin tipo, ordenados
 */
public record MayorControl(
        int totalMovimientos,
        int movimientosExcluidos,
        List<MayorModulo> modulos,
        List<Integer> comprobantesSinTipo) {

    public MayorControl {
        modulos = modulos == null ? List.of() : List.copyOf(modulos);
        comprobantesSinTipo = comprobantesSinTipo == null
                ? List.of() : List.copyOf(comprobantesSinTipo);
    }

    public static MayorControl vacio() {
        return new MayorControl(0, 0, List.of(), List.of());
    }

}
