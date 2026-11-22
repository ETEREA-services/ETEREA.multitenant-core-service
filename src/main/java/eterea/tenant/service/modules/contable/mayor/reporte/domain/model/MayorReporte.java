package eterea.tenant.service.modules.contable.mayor.reporte.domain.model;

import eterea.tenant.service.modules.contable.mayor.domain.model.MayorControl;
import eterea.tenant.service.modules.reporte.api.ReporteTabla;

import java.util.Objects;

/**
 * Resultado del reporte del Mayor: tabla y controles operativos.
 */
public record MayorReporte(ReporteTabla tabla, MayorControl control) {

    public MayorReporte {
        tabla = Objects.requireNonNull(tabla, "tabla");
        control = Objects.requireNonNull(control, "control");
    }

}
