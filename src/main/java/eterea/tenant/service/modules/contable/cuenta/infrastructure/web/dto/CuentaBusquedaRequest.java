package eterea.tenant.service.modules.contable.cuenta.infrastructure.web.dto;

import jakarta.validation.constraints.NotNull;

/**
 * Texto de búsqueda legacy de cuentas.
 */
public record CuentaBusquedaRequest(@NotNull String texto) {
}
