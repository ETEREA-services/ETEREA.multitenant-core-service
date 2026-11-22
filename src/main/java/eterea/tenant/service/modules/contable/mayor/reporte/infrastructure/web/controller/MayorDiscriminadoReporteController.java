package eterea.tenant.service.modules.contable.mayor.reporte.infrastructure.web.controller;

import eterea.tenant.service.modules.contable.mayor.domain.model.MayorDiscriminadoFiltro;
import eterea.tenant.service.modules.contable.mayor.reporte.domain.ports.in.GenerarMayorDiscriminadoReporteUseCase;
import eterea.tenant.service.modules.contable.mayor.reporte.infrastructure.web.MayorReporteArchivo;
import eterea.tenant.service.modules.contable.mayor.reporte.infrastructure.web.dto.MayorDiscriminadoFiltroRequest;
import eterea.tenant.service.modules.reporte.api.GenerarReportePdfUseCase;
import eterea.tenant.service.modules.reporte.api.GenerarReporteXlsxUseCase;
import eterea.tenant.service.modules.reporte.api.ReporteDatosResponse;
import eterea.tenant.service.modules.reporte.api.ReporteDtoMapper;
import eterea.tenant.service.modules.reporte.api.ReporteTabla;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Reporte del Mayor Discriminado: vista previa, PDF y XLSX.
 */
@RestController
@RequestMapping("/api/tenant/core/reporte/mayor-discriminado")
@RequiredArgsConstructor
public class MayorDiscriminadoReporteController {

    private static final MediaType XLSX = MediaType.parseMediaType(
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

    private final GenerarMayorDiscriminadoReporteUseCase generarMayorDiscriminadoReporte;
    private final GenerarReportePdfUseCase generarReportePdf;
    private final GenerarReporteXlsxUseCase generarReporteXlsx;
    private final ReporteDtoMapper reporteDtoMapper;

    @PostMapping(value = "/datos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReporteDatosResponse> datos(
            @Valid @RequestBody MayorDiscriminadoFiltroRequest request) {
        MayorDiscriminadoFiltro filtro = request.toFiltro();
        ReporteTabla tabla = generarMayorDiscriminadoReporte.generar(filtro);
        return ResponseEntity.ok(reporteDtoMapper.toResponse(tabla));
    }

    @PostMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> pdf(
            @Valid @RequestBody MayorDiscriminadoFiltroRequest request) {
        MayorDiscriminadoFiltro filtro = request.toFiltro();
        ReporteTabla tabla = generarMayorDiscriminadoReporte.generar(filtro);
        byte[] contenido = generarReportePdf.generar(tabla);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        MayorReporteArchivo.nombre(
                                tabla, filtro.desde(), filtro.hasta(), "pdf"))
                .contentLength(contenido.length)
                .body(contenido);
    }

    @PostMapping(value = "/xlsx", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public ResponseEntity<byte[]> xlsx(
            @Valid @RequestBody MayorDiscriminadoFiltroRequest request) {
        MayorDiscriminadoFiltro filtro = request.toFiltro();
        ReporteTabla tabla = generarMayorDiscriminadoReporte.generar(filtro);
        byte[] contenido = generarReporteXlsx.generar(tabla);
        return ResponseEntity.ok()
                .contentType(XLSX)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        MayorReporteArchivo.nombre(
                                tabla, filtro.desde(), filtro.hasta(), "xlsx"))
                .contentLength(contenido.length)
                .body(contenido);
    }

}
