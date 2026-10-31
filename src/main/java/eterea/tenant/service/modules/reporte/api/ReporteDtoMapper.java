package eterea.tenant.service.modules.reporte.api;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Convierte la tabla de dominio al contrato JSON de vista previa.
 */
@Component
public class ReporteDtoMapper {

    public ReporteDatosResponse toResponse(ReporteTabla tabla) {
        List<ReporteColumnaResponse> columnas = tabla.columnas().stream()
                .map(columna -> new ReporteColumnaResponse(
                        columna.clave(),
                        columna.titulo(),
                        columna.alineacion(),
                        columna.anchoRelativo(),
                        columna.admiteSangria()))
                .toList();

        List<ReporteFilaResponse> filas = tabla.filas().stream()
                .map(fila -> new ReporteFilaResponse(
                        fila.nivel(),
                        fila.negrita(),
                        fila.celdas()))
                .toList();

        return new ReporteDatosResponse(
                tabla.cabecera().codigo(),
                tabla.cabecera().titulo(),
                tabla.cabecera().negocio(),
                ReporteFecha.formatear(tabla.cabecera().fecha()),
                columnas,
                filas,
                tabla.totalFilas());
    }

}
