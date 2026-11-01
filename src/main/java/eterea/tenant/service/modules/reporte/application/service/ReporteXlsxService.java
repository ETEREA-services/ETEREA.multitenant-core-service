package eterea.tenant.service.modules.reporte.application.service;

import eterea.tenant.service.modules.reporte.api.GenerarReporteXlsxUseCase;
import eterea.tenant.service.modules.reporte.api.ReporteTabla;
import eterea.tenant.service.modules.reporte.domain.ports.out.ReporteXlsxPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReporteXlsxService implements GenerarReporteXlsxUseCase {

    private final ReporteXlsxPort reporteXlsxPort;

    @Override
    public byte[] generar(ReporteTabla tabla) {
        return reporteXlsxPort.generar(tabla);
    }

}
