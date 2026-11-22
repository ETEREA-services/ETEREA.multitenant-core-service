package eterea.tenant.service.modules.contable.mayor.reporte.infrastructure.web.dto;

import java.util.List;

/**
 * Controles operativos que acompañan a la vista previa del Mayor.
 */
public record MayorControlResponse(
        int totalMovimientos,
        int movimientosExcluidos,
        List<MayorModuloResponse> modulos,
        List<Integer> comprobantesSinTipo) {

    public MayorControlResponse {
        modulos = modulos == null ? List.of() : List.copyOf(modulos);
        comprobantesSinTipo = comprobantesSinTipo == null
                ? List.of() : List.copyOf(comprobantesSinTipo);
    }

}
