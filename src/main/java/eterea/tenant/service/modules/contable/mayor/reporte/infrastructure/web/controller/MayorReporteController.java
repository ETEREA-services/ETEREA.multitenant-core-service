package eterea.tenant.service.modules.contable.mayor.reporte.infrastructure.web.controller;

import eterea.tenant.service.modules.contable.mayor.domain.model.MayorFiltro;
import eterea.tenant.service.modules.contable.mayor.reporte.domain.model.MayorReporte;
import eterea.tenant.service.modules.contable.mayor.reporte.domain.ports.in.GenerarMayorReporteUseCase;
import eterea.tenant.service.modules.contable.mayor.reporte.infrastructure.web.MayorReporteArchivo;
import eterea.tenant.service.modules.contable.mayor.reporte.infrastructure.web.dto.MayorDatosResponse;
import eterea.tenant.service.modules.contable.mayor.reporte.infrastructure.web.dto.MayorFiltroRequest;
import eterea.tenant.service.modules.contable.mayor.reporte.infrastructure.web.mapper.MayorDtoMapper;
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
 * Reporte del Mayor General: vista previa, PDF y XLSX.
 */
@RestController
@RequestMapping("/api/tenant/core/reporte/mayor")
@RequiredArgsConstructor
public class MayorReporteController {

    private static final MediaType XLSX = MediaType.parseMediaType(
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

    private final GenerarMayorReporteUseCase generarMayorReporte;
    private final GenerarReportePdfUseCase generarReportePdf;
    private final GenerarReporteXlsxUseCase generarReporteXlsx;
    private final ReporteDtoMapper reporteDtoMapper;
    private final MayorDtoMapper mayorDtoMapper;

    @PostMapping(value = "/datos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MayorDatosResponse> datos(
            @Valid @RequestBody MayorFiltroRequest request) {
        MayorFiltro filtro = request.toFiltro();
        MayorReporte reporte = generarMayorReporte.generar(filtro);
        ReporteDatosResponse tabla = reporteDtoMapper.toResponse(reporte.tabla());
        return ResponseEntity.ok(new MayorDatosResponse(
                tabla,
                mayorDtoMapper.toControl(reporte.control())));
    }

    @PostMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> pdf(@Valid @RequestBody MayorFiltroRequest request) {
        MayorFiltro filtro = request.toFiltro();
        ReporteTabla tabla = generarMayorReporte.generar(filtro).tabla();
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
    public ResponseEntity<byte[]> xlsx(@Valid @RequestBody MayorFiltroRequest request) {
        MayorFiltro filtro = request.toFiltro();
        ReporteTabla tabla = generarMayorReporte.generar(filtro).tabla();
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
