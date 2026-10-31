package eterea.tenant.service.modules.contable.cuenta.reporte.infrastructure.web.controller;

import eterea.tenant.service.modules.contable.cuenta.reporte.domain.ports.in.GenerarPlanCuentasReporteUseCase;
import eterea.tenant.service.modules.reporte.api.GenerarReportePdfUseCase;
import eterea.tenant.service.modules.reporte.api.ReporteDatosResponse;
import eterea.tenant.service.modules.reporte.api.ReporteDtoMapper;
import eterea.tenant.service.modules.reporte.api.ReporteFecha;
import eterea.tenant.service.modules.reporte.api.ReporteTabla;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.Locale;

/**
 * Listado e impresión del plan de cuentas.
 *
 * <p>Los dos endpoints comparten el mismo caso de uso: {@code /datos} devuelve
 * el contrato JSON para la vista previa y {@code /pdf} el documento imprimible.
 * El tenant se resuelve en el backend a partir de {@code X-Tenant-Id}.</p>
 */
@RestController
@RequestMapping("/api/tenant/core/reporte/plan-cuentas")
@RequiredArgsConstructor
public class PlanCuentasReporteController {

    private final GenerarPlanCuentasReporteUseCase generarPlanCuentasReporte;
    private final GenerarReportePdfUseCase generarReportePdf;
    private final ReporteDtoMapper reporteDtoMapper;

    @PostMapping(value = "/datos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReporteDatosResponse> datos() {
        return ResponseEntity.ok(reporteDtoMapper.toResponse(generarPlanCuentasReporte.generar()));
    }

    @PostMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> pdf() {
        ReporteTabla tabla = generarPlanCuentasReporte.generar();
        byte[] contenido = generarReportePdf.generar(tabla);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, nombreArchivo(tabla))
                .contentLength(contenido.length)
                .body(contenido);
    }

    private String nombreArchivo(ReporteTabla tabla) {
        String negocio = slug(tabla.cabecera().negocio());
        String fecha = ReporteFecha.formatear(tabla.cabecera().fecha());
        String archivo = "plan-cuentas-"
                + (negocio.isBlank() ? "negocio" : negocio)
                + "-"
                + fecha
                + ".pdf";

        return ContentDisposition.attachment()
                .filename(archivo, StandardCharsets.UTF_8)
                .build()
                .toString();
    }

    private static String slug(String valor) {
        if (valor == null) {
            return "";
        }
        return Normalizer.normalize(valor, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-+|-+$)", "");
    }

}
