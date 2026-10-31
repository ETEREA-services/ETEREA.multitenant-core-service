package eterea.tenant.service.modules.platform.api;

/**
 * Datos mínimos del negocio activo que necesitan los reportes.
 *
 * @param negocioId identificador legacy del negocio ({@code negocio.neg_id})
 * @param nombre    nombre visible del negocio
 */
public record NegocioActualInfo(Integer negocioId, String nombre) {
}
