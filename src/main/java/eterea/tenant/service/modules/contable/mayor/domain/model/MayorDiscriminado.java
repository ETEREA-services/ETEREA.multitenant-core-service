package eterea.tenant.service.modules.contable.mayor.domain.model;

import java.util.List;

/**
 * Resultado del Mayor Discriminado.
 *
 * <p>Las advertencias incluyen cuentas inexistentes y movimientos excluidos por
 * no tener tipo de comprobante (D9-C).</p>
 */
public record MayorDiscriminado(
        List<MayorDiscriminadoFila> filas,
        List<String> advertencias) {

    public MayorDiscriminado {
        filas = filas == null ? List.of() : List.copyOf(filas);
        advertencias = advertencias == null ? List.of() : List.copyOf(advertencias);
    }

}
