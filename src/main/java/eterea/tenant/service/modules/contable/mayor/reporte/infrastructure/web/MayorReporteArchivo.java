package eterea.tenant.service.modules.contable.mayor.reporte.infrastructure.web;

import eterea.tenant.service.modules.reporte.api.ReporteFecha;
import eterea.tenant.service.modules.reporte.api.ReporteTabla;
import org.springframework.http.ContentDisposition;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.text.Normalizer;
import java.util.Locale;

/**
 * Nombre de archivo común para PDF/XLSX del Mayor.
 */
public final class MayorReporteArchivo {

    private MayorReporteArchivo() {
    }

    public static String nombre(
            ReporteTabla tabla, LocalDate desde, LocalDate hasta, String extension) {
        String negocio = slug(tabla.cabecera().negocio());
        String archivo = tabla.cabecera().codigo()
                + "-" + (negocio.isBlank() ? "negocio" : negocio)
                + "-" + ReporteFecha.formatear(desde)
                + "-" + ReporteFecha.formatear(hasta)
                + "." + extension;
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
