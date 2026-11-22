package eterea.tenant.service.modules.contable.mayor.reporte.application.usecases;

import eterea.tenant.service.modules.contable.mayor.domain.model.MayorDiscriminado;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorDiscriminadoFila;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorDiscriminadoFiltro;
import eterea.tenant.service.modules.contable.mayor.domain.ports.in.GenerarMayorDiscriminadoUseCase;
import eterea.tenant.service.modules.contable.moneda.domain.model.MonedaInfo;
import eterea.tenant.service.modules.contable.moneda.domain.ports.out.MonedaQueryPort;
import eterea.tenant.service.modules.platform.api.NegocioActual;
import eterea.tenant.service.modules.platform.api.NegocioActualInfo;
import eterea.tenant.service.modules.reporte.api.ReporteTabla;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenerarMayorDiscriminadoReporteUseCaseImplTest {

    private static final LocalDate DIA = LocalDate.of(2026, 11, 11);

    @Mock
    private GenerarMayorDiscriminadoUseCase generarMayorDiscriminado;
    @Mock
    private NegocioActual negocioActual;
    @Mock
    private MonedaQueryPort monedaQuery;

    @InjectMocks
    private GenerarMayorDiscriminadoReporteUseCaseImpl useCase;

    @Test
    void armaLaTablaConLasNueveColumnasYAdvertencias() {
        when(generarMayorDiscriminado.generar(any())).thenReturn(new MayorDiscriminado(
                List.of(
                        new MayorDiscriminadoFila(
                                DIA, 21201001L, "IVA Débito Fiscal", "Factura",
                                "Concepto", 42, BigDecimal.ZERO,
                                new BigDecimal("10.00"), "Proveedor")),
                List.of("La cuenta 999 no existe en el plan del negocio.")));
        prepararEntorno();

        ReporteTabla tabla = useCase.generar(filtro());

        assertThat(tabla.cabecera().codigo()).isEqualTo("mayor-discriminado");
        assertThat(tabla.cabecera().titulo()).isEqualTo("Mayor Discriminado");
        assertThat(tabla.cabecera().subtitulo()).isEqualTo(
                "Cuentas: 2 · Período: 11-11-2026 - 11-11-2026"
                        + " · Registrado en ARS · Expresado en ARS");
        assertThat(tabla.columnas()).hasSize(9);
        assertThat(tabla.filas()).hasSize(1);
        assertThat(tabla.filas().getFirst().celdas())
                .containsExactly("11-11-2026", "21201001", "IVA Débito Fiscal",
                        "Factura", "Concepto", "42", "", "10,00", "Proveedor");
        assertThat(tabla.advertencias())
                .contains("La cuenta 999 no existe en el plan del negocio.");
    }

    @Test
    void advierteCuandoNoHayMovimientos() {
        when(generarMayorDiscriminado.generar(any()))
                .thenReturn(new MayorDiscriminado(List.of(), List.of()));
        prepararEntorno();

        ReporteTabla tabla = useCase.generar(filtro());

        assertThat(tabla.filas()).isEmpty();
        assertThat(tabla.advertencias())
                .containsExactly("No hay movimientos para las cuentas y el período seleccionados.");
    }

    private void prepararEntorno() {
        when(negocioActual.findActual())
                .thenReturn(Optional.of(new NegocioActualInfo(54, "TSA Agencia")));
        when(monedaQuery.findById(1))
                .thenReturn(Optional.of(new MonedaInfo(1, "Peso Argentino", "ARS", true)));
    }

    private static MayorDiscriminadoFiltro filtro() {
        return new MayorDiscriminadoFiltro(
                List.of(21201001L, 21101001L), DIA, DIA, 1, 1);
    }

}
