package eterea.tenant.service.modules.contable.balance.reporte.infrastructure.web.controller;

import eterea.tenant.service.modules.contable.balance.domain.model.BalanceFiltro;
import eterea.tenant.service.modules.contable.balance.domain.ports.out.MonedaQueryPort;
import eterea.tenant.service.modules.contable.balance.reporte.domain.ports.in.GenerarBalanceReporteUseCase;
import eterea.tenant.service.modules.contable.balance.reporte.infrastructure.web.dto.BalanceFiltroRequest;
import eterea.tenant.service.modules.contable.balance.reporte.infrastructure.web.dto.MonedasResponse;
import eterea.tenant.service.modules.reporte.api.GenerarReportePdfUseCase;
import eterea.tenant.service.modules.reporte.api.GenerarReporteXlsxUseCase;
import eterea.tenant.service.modules.reporte.api.ReporteDatosResponse;
import eterea.tenant.service.modules.reporte.api.ReporteDtoMapper;
import eterea.tenant.service.modules.reporte.api.ReporteFecha;
import eterea.tenant.service.modules.reporte.api.ReporteTabla;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.Locale;

/**
 * Reporte del balance: vista previa, PDF y catálogo de monedas.
 *
 * <p>Los tres endpoints comparten el mismo caso de uso y el mismo
 * {@link ReporteTabla}; el tenant se resuelve por {@code X-Tenant-Id}.</p>
 */
@RestController
@RequestMapping("/api/tenant/core/reporte/balance")
@RequiredArgsConstructor
public class BalanceReporteController {

    private final GenerarBalanceReporteUseCase generarBalanceReporte;
    private final GenerarReportePdfUseCase generarReportePdf;
    private final GenerarReporteXlsxUseCase generarReporteXlsx;
    private final ReporteDtoMapper reporteDtoMapper;
    private final MonedaQueryPort monedaQuery;

    @PostMapping(value = "/datos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReporteDatosResponse> datos(
            @Valid @RequestBody BalanceFiltroRequest request) {
        BalanceFiltro filtro = request.toFiltro();
        ReporteTabla tabla = generarBalanceReporte.generar(filtro);
        return ResponseEntity.ok(reporteDtoMapper.toResponse(tabla));
    }

    @PostMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> pdf(@Valid @RequestBody BalanceFiltroRequest request) {
        BalanceFiltro filtro = request.toFiltro();
        ReporteTabla tabla = generarBalanceReporte.generar(filtro);
        byte[] contenido = generarReportePdf.generar(tabla);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, nombreArchivo(tabla, filtro, "pdf"))
                .contentLength(contenido.length)
                .body(contenido);
    }

    @PostMapping(value = "/xlsx",
            produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public ResponseEntity<byte[]> xlsx(@Valid @RequestBody BalanceFiltroRequest request) {
        BalanceFiltro filtro = request.toFiltro();
        ReporteTabla tabla = generarBalanceReporte.generar(filtro);
        byte[] contenido = generarReporteXlsx.generar(tabla);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .header(HttpHeaders.CONTENT_DISPOSITION, nombreArchivo(tabla, filtro, "xlsx"))
                .contentLength(contenido.length)
                .body(contenido);
    }

    @GetMapping(value = "/monedas", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MonedasResponse> monedas() {
        return ResponseEntity.ok(new MonedasResponse(monedaQuery.findAll()));
    }

    private String nombreArchivo(ReporteTabla tabla, BalanceFiltro filtro, String extension) {
        String negocio = slug(tabla.cabecera().negocio());
        String archivo = tabla.cabecera().codigo()
                + "-" + (negocio.isBlank() ? "negocio" : negocio)
                + "-" + ReporteFecha.formatear(filtro.desde())
                + "-" + ReporteFecha.formatear(filtro.hasta())
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
