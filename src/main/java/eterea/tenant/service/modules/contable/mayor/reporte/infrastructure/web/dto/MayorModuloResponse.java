package eterea.tenant.service.modules.contable.mayor.reporte.infrastructure.web.dto;

/**
 * Módulo y cantidad de movimientos para el panel de control.
 */
public record MayorModuloResponse(Integer moduloId, String nombre, int cantidad) {
}
