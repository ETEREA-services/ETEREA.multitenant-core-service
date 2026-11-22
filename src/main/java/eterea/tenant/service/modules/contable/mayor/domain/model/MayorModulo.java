package eterea.tenant.service.modules.contable.mayor.domain.model;

/**
 * Cantidad de movimientos de una cuenta agrupados por módulo.
 */
public record MayorModulo(Integer moduloId, String nombre, int cantidad) {

    public MayorModulo {
        nombre = nombre == null ? "" : nombre;
    }

}
