package eterea.tenant.service.modules.reporte.application.service;

import eterea.tenant.service.modules.reporte.api.GenerarReportePdfUseCase;
import eterea.tenant.service.modules.reporte.api.ReporteTabla;
import eterea.tenant.service.modules.reporte.domain.ports.out.ReportePdfPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportePdfService implements GenerarReportePdfUseCase {

    private final ReportePdfPort reportePdfPort;

    @Override
    public byte[] generar(ReporteTabla tabla) {
        return reportePdfPort.generar(tabla);
    }

}
