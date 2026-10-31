package eterea.tenant.service.modules.contable.cuenta.reporte.application.usecases;

import eterea.tenant.service.modules.contable.cuenta.domain.model.Cuenta;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.out.CuentaRepository;
import eterea.tenant.service.modules.platform.api.NegocioActual;
import eterea.tenant.service.modules.platform.api.NegocioActualInfo;
import eterea.tenant.service.modules.reporte.api.ReporteException;
import eterea.tenant.service.modules.reporte.api.ReporteFecha;
import eterea.tenant.service.modules.reporte.api.ReporteTabla;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenerarPlanCuentasReporteUseCaseImplTest {

    @Mock
    private CuentaRepository cuentaRepository;

    @Mock
    private NegocioActual negocioActual;

    private GenerarPlanCuentasReporteUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new GenerarPlanCuentasReporteUseCaseImpl(cuentaRepository, negocioActual);
    }

    @Test
    void armaElListadoConLasTresColumnasYLaJerarquiaDelLegacy() {
        when(negocioActual.findActual())
                .thenReturn(Optional.of(new NegocioActualInfo(54, "TSA Agencia")));
        when(cuentaRepository.findAllByNegocioIdOrdered(54)).thenReturn(List.of(
                cuenta(10_000_000L, "ACTIVO", 1, BigDecimal.ZERO),
                cuenta(11_000_000L, "DISPONIBILIDADES", 2, BigDecimal.ZERO),
                cuenta(11_101_001L, "Caja", 5, new BigDecimal("10101001002"))));

        ReporteTabla tabla = useCase.generar();

        assertThat(tabla.cabecera().codigo()).isEqualTo("plan-cuentas");
        assertThat(tabla.cabecera().titulo()).isEqualTo("Plan de Cuentas");
        assertThat(tabla.cabecera().negocio()).isEqualTo("TSA Agencia");
        assertThat(tabla.cabecera().fecha()).isEqualTo(LocalDate.now());
        assertThat(ReporteFecha.formatear(tabla.cabecera().fecha()))
                .matches("\\d{2}-\\d{2}-\\d{4}");

        assertThat(tabla.columnas()).hasSize(3);
        assertThat(tabla.columnas())
                .extracting(columna -> columna.titulo())
                .containsExactly("Código", "Cuenta", "Cuenta maestra");

        assertThat(tabla.filas()).hasSize(3);
        assertThat(tabla.filas().get(0).celdas())
                .containsExactly("10000000", "ACTIVO", "");
        assertThat(tabla.filas().get(0).nivel()).isEqualTo(1);
        assertThat(tabla.filas().get(0).negrita()).isTrue();

        assertThat(tabla.filas().get(1).nivel()).isEqualTo(2);
        assertThat(tabla.filas().get(1).negrita()).isTrue();
        assertThat(tabla.filas().get(1).celdas().get(2)).isEmpty();

        assertThat(tabla.filas().get(2).celdas())
                .containsExactly("11101001", "Caja", "10101001002");
        assertThat(tabla.filas().get(2).nivel()).isEqualTo(5);
        assertThat(tabla.filas().get(2).negrita()).isFalse();
        assertThat(tabla.totalFilas()).isEqualTo(3);
    }

    @Test
    void usaElGradoAlmacenadoParaLaSangriaYLaNegrita() {
        when(negocioActual.findActual())
                .thenReturn(Optional.of(new NegocioActualInfo(54, "TSA Agencia")));
        when(cuentaRepository.findAllByNegocioIdOrdered(54)).thenReturn(List.of(
                cuenta(44_000_000L, "INGRESOS AGENCIA", 2, BigDecimal.ZERO),
                cuenta(44_100_000L, "INGRESOS AGENCIA RECEPTIVO CAC", 3, BigDecimal.ZERO),
                cuenta(44_101_000L, "Hotelería Cacheuta", 4, new BigDecimal("40101001000"))));

        ReporteTabla tabla = useCase.generar();

        assertThat(tabla.filas())
                .extracting(fila -> fila.nivel())
                .containsExactly(2, 3, 4);
        assertThat(tabla.filas())
                .extracting(fila -> fila.negrita())
                .containsExactly(true, true, true);
    }

    @Test
    void fallaSiNoHayNegocioActivo() {
        when(negocioActual.findActual()).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.generar())
                .isInstanceOf(ReporteException.class)
                .satisfies(exception -> assertThat(((ReporteException) exception).getCodigo())
                        .isEqualTo("REPORTE_SIN_NEGOCIO"));
    }

    private static Cuenta cuenta(Long numeroCuenta, String nombre, int grado,
                                 BigDecimal cuentaMaestro) {
        return Cuenta.builder()
                .numeroCuenta(numeroCuenta)
                .nombre(nombre)
                .negocioId(54)
                .grado(grado)
                .cuentaMaestro(cuentaMaestro)
                .build();
    }

}
