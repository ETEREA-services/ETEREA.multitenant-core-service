package eterea.tenant.service.modules.contable.cuenta.infrastructure.web.dto;

/**
 * Resultado de la búsqueda legacy de cuentas.
 *
 * @param descripcion texto visible {@code nombre (cuenta)}
 */
public record CuentaBusquedaResponse(
        long numeroCuenta,
        String nombre,
        String descripcion) {
}
