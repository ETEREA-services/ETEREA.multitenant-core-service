package eterea.tenant.service.modules.reporte.api;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Locale;

/**
 * Alineación horizontal de una columna de reporte.
 */
public enum ReporteAlineacion {

    IZQUIERDA,
    DERECHA,
    CENTRO;

    @JsonValue
    public String valor() {
        return name().toLowerCase(Locale.ROOT);
    }

}
