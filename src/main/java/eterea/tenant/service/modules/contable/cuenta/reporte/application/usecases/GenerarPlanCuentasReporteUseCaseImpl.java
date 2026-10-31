package eterea.tenant.service.modules.contable.cuenta.reporte.application.usecases;

import eterea.tenant.service.modules.contable.cuenta.domain.model.CodigoCuenta;
import eterea.tenant.service.modules.contable.cuenta.domain.model.Cuenta;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.out.CuentaRepository;
import eterea.tenant.service.modules.contable.cuenta.reporte.domain.ports.in.GenerarPlanCuentasReporteUseCase;
import eterea.tenant.service.modules.platform.api.NegocioActual;
import eterea.tenant.service.modules.platform.api.NegocioActualInfo;
import eterea.tenant.service.modules.reporte.api.ReporteAlineacion;
import eterea.tenant.service.modules.reporte.api.ReporteCabecera;
import eterea.tenant.service.modules.reporte.api.ReporteColumna;
import eterea.tenant.service.modules.reporte.api.ReporteException;
import eterea.tenant.service.modules.reporte.api.ReporteFila;
import eterea.tenant.service.modules.reporte.api.ReporteTabla;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Arma el listado del plan de cuentas con la misma jerarquía y el mismo orden
 * que el legacy.
 *
 * <p>La sangría y la negrita salen de {@code pla_grado}: las cuentas de grado 1
 * a 4 son agrupadoras y van en negrita; las de grado 5 son imputables.</p>
 */
@Component
@RequiredArgsConstructor
public class GenerarPlanCuentasReporteUseCaseImpl implements GenerarPlanCuentasReporteUseCase {

    private static final List<ReporteColumna> COLUMNAS = List.of(
            new ReporteColumna("codigo", "Código", ReporteAlineacion.DERECHA, 0.18f, false),
            new ReporteColumna("nombre", "Cuenta", ReporteAlineacion.IZQUIERDA, 0.60f, true),
            new ReporteColumna("cuentaMaestra", "Cuenta maestra",
                    ReporteAlineacion.DERECHA, 0.22f, false));

    private static final int GRADO_MAXIMO = 5;
    private static final int GRADO_IMPUTABLE = 5;

    private final CuentaRepository cuentaRepository;
    private final NegocioActual negocioActual;

    @Override
    public ReporteTabla generar() {
        NegocioActualInfo negocio = negocioActual.findActual()
                .orElseThrow(() -> new ReporteException(
                        "REPORTE_SIN_NEGOCIO",
                        "No hay un negocio activo para generar el reporte."));

        List<ReporteFila> filas = cuentaRepository
                .findAllByNegocioIdOrdered(negocio.negocioId())
                .stream()
                .map(GenerarPlanCuentasReporteUseCaseImpl::toFila)
                .toList();

        return new ReporteTabla(
                new ReporteCabecera(
                        "plan-cuentas",
                        "Plan de Cuentas",
                        negocio.nombre(),
                        LocalDate.now()),
                COLUMNAS,
                filas);
    }

    private static ReporteFila toFila(Cuenta cuenta) {
        int nivel = nivel(cuenta);
        String codigo = String.valueOf(cuenta.getNumeroCuenta());
        String nombre = cuenta.getNombre() == null ? "" : cuenta.getNombre();
        String cuentaMaestra = cuentaMaestra(cuenta.getCuentaMaestro());

        return new ReporteFila(
                nivel,
                nivel < GRADO_IMPUTABLE,
                List.of(codigo, nombre, cuentaMaestra));
    }

    private static int nivel(Cuenta cuenta) {
        Integer grado = cuenta.getGrado();
        if (grado != null && grado > 0) {
            return Math.min(grado, GRADO_MAXIMO);
        }
        return CodigoCuenta.of(cuenta.getNumeroCuenta()).nivel();
    }

    private static String cuentaMaestra(BigDecimal cuentaMaestro) {
        if (cuentaMaestro == null || cuentaMaestro.signum() == 0) {
            return "";
        }
        return cuentaMaestro.toPlainString();
    }

}
