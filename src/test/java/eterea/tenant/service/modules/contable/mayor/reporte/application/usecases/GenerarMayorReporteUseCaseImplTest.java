package eterea.tenant.service.modules.contable.mayor.reporte.application.usecases;

import eterea.tenant.service.modules.contable.mayor.domain.exception.MayorException;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorControl;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorCuenta;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorFiltro;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorModulo;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorMovimiento;
import eterea.tenant.service.modules.contable.mayor.domain.ports.in.GenerarMayorUseCase;
import eterea.tenant.service.modules.contable.mayor.reporte.domain.model.MayorReporte;
import eterea.tenant.service.modules.contable.moneda.domain.model.MonedaInfo;
import eterea.tenant.service.modules.contable.moneda.domain.ports.out.MonedaQueryPort;
import eterea.tenant.service.modules.platform.api.NegocioActual;
import eterea.tenant.service.modules.platform.api.NegocioActualInfo;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenerarMayorReporteUseCaseImplTest {

    private static final LocalDate DIA = LocalDate.of(2026, 11, 11);

    @Mock
    private GenerarMayorUseCase generarMayor;
    @Mock
    private NegocioActual negocioActual;
    @Mock
    private MonedaQueryPort monedaQuery;

    @InjectMocks
    private GenerarMayorReporteUseCaseImpl useCase;

    @Test
    void armaLaTablaConSaldoAnteriorMovimientosYTotal() {
        when(generarMayor.generar(any())).thenReturn(mayor());
        prepararEntorno();

        MayorReporte reporte = useCase.generar(filtro(false, 1, 1));

        assertThat(reporte.tabla().cabecera().codigo()).isEqualTo("mayor");
        assertThat(reporte.tabla().cabecera().titulo()).isEqualTo("Mayor General");
        assertThat(reporte.tabla().cabecera().negocio()).isEqualTo("TSA Agencia");
        assertThat(reporte.tabla().cabecera().subtitulo()).isEqualTo(
                "Cuenta 21201001 - IVA Débito Fiscal · Período: 11-11-2026 - 11-11-2026"
                        + " · Registrado en ARS · Expresado en ARS");
        assertThat(reporte.tabla().columnas()).hasSize(8);
        assertThat(reporte.tabla().filas()).hasSize(4);

        assertThat(reporte.tabla().filas().get(0).celdas())
                .containsExactly("", "Saldo anterior", "", "", "", "", "100,00", "");
        assertThat(reporte.tabla().filas().get(1).celdas())
                .containsExactly("11-11-2026", "Factura", "Concepto", "42",
                        "10,00", "", "110,00", "Proveedor");
        assertThat(reporte.tabla().filas().get(2).celdas())
                .containsExactly("12-11-2026", "Nota de crédito", "Concepto", "43",
                        "", "5,00", "105,00", "");
        assertThat(reporte.tabla().filas().get(3).celdas())
                .containsExactly("", "Total", "", "", "10,00", "5,00", "105,00", "");
        assertThat(reporte.tabla().advertencias()).isEmpty();
        assertThat(reporte.control().totalMovimientos()).isEqualTo(2);
    }

    @Test
    void sinSaldoAnteriorOmiteLaFilaDeApertura() {
        when(generarMayor.generar(any())).thenReturn(mayor());
        prepararEntorno();

        MayorReporte reporte = useCase.generar(filtro(true, 1, 1));

        assertThat(reporte.tabla().filas()).hasSize(3);
        assertThat(reporte.tabla().filas().getFirst().celdas().get(1)).isEqualTo("Factura");
    }

    @Test
    void advierteCuentaSinMovimientosNiSaldo() {
        when(generarMayor.generar(any())).thenReturn(mayorVacio(BigDecimal.ZERO, 0));
        prepararEntorno();

        MayorReporte reporte = useCase.generar(filtro(false, 1, 1));

        assertThat(reporte.tabla().advertencias())
                .containsExactly("La cuenta no tiene movimientos en el período.");
        assertThat(reporte.tabla().filas()).hasSize(2);
    }

    @Test
    void advierteComprobantesExcluidos() {
        when(generarMayor.generar(any())).thenReturn(mayorVacio(BigDecimal.ZERO, 4));
        prepararEntorno();

        MayorReporte reporte = useCase.generar(filtro(false, 1, 1));

        assertThat(reporte.tabla().advertencias())
                .anyMatch(advertencia -> advertencia.contains("Se excluyeron 4")
                        && advertencia.contains("141"));
    }

    @Test
    void fallaSiLaMonedaNoExiste() {
        when(generarMayor.generar(any())).thenReturn(mayor());
        when(negocioActual.findActual())
                .thenReturn(Optional.of(new NegocioActualInfo(54, "TSA Agencia")));
        when(monedaQuery.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.generar(filtro(false, 99, 99)))
                .isInstanceOf(MayorException.class)
                .extracting("codigo")
                .isEqualTo("MAYOR_MONEDA_INVALIDA");
    }

    private void prepararEntorno() {
        when(negocioActual.findActual())
                .thenReturn(Optional.of(new NegocioActualInfo(54, "TSA Agencia")));
        when(monedaQuery.findById(1))
                .thenReturn(Optional.of(new MonedaInfo(1, "Peso Argentino", "ARS", true)));
    }

    private static MayorFiltro filtro(boolean sinSaldo, int origen, int destino) {
        return new MayorFiltro(21201001L, DIA, DIA, sinSaldo, origen, destino);
    }

    private static MayorCuenta mayor() {
        MayorControl control = new MayorControl(
                2, 0, List.of(new MayorModulo(1, "Ventas", 2)), List.of());
        return new MayorCuenta(
                21201001L,
                "IVA Débito Fiscal",
                new BigDecimal("100.00"),
                List.of(
                        new MayorMovimiento(
                                DIA, "Factura", "Concepto", 42,
                                new BigDecimal("10.00"), BigDecimal.ZERO,
                                new BigDecimal("110.00"), "Proveedor"),
                        new MayorMovimiento(
                                DIA.plusDays(1), "Nota de crédito", "Concepto", 43,
                                BigDecimal.ZERO, new BigDecimal("5.00"),
                                new BigDecimal("105.00"), "")),
                new BigDecimal("10.00"),
                new BigDecimal("5.00"),
                new BigDecimal("105.00"),
                control);
    }

    private static MayorCuenta mayorVacio(BigDecimal saldoAnterior, int excluidos) {
        MayorControl control = new MayorControl(
                0, excluidos, List.of(), excluidos == 0 ? List.of() : List.of(141));
        return new MayorCuenta(
                21201001L,
                "IVA Débito Fiscal",
                saldoAnterior,
                List.of(),
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                saldoAnterior,
                control);
    }

}
