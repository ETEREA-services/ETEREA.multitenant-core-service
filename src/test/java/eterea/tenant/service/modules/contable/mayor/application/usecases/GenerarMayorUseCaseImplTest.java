package eterea.tenant.service.modules.contable.mayor.application.usecases;

import eterea.tenant.service.modules.contable.cuenta.domain.model.Cuenta;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.out.CuentaRepository;
import eterea.tenant.service.modules.contable.mayor.application.service.MayorCalculator;
import eterea.tenant.service.modules.contable.mayor.domain.exception.MayorException;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorControl;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorCuenta;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorDetalle;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorFiltro;
import eterea.tenant.service.modules.contable.mayor.domain.ports.out.MayorControlPort;
import eterea.tenant.service.modules.contable.mayor.domain.ports.out.MayorMovimientoPort;
import eterea.tenant.service.modules.contable.moneda.domain.exception.MonedaException;
import eterea.tenant.service.modules.contable.moneda.domain.ports.out.CotizacionOnDemandPort;
import eterea.tenant.service.modules.platform.api.NegocioActual;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenerarMayorUseCaseImplTest {

    private static final LocalDate DIA = LocalDate.of(2026, 11, 11);

    @Mock
    private CuentaRepository cuentaRepository;
    @Mock
    private MayorMovimientoPort mayorMovimientoPort;
    @Mock
    private MayorControlPort mayorControlPort;
    @Mock
    private CotizacionOnDemandPort cotizacionOnDemand;
    @Mock
    private NegocioActual negocioActual;

    private GenerarMayorUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new GenerarMayorUseCaseImpl(
                cuentaRepository,
                mayorMovimientoPort,
                mayorControlPort,
                cotizacionOnDemand,
                new MayorCalculator(),
                negocioActual);
    }

    @Test
    void fallaSinNegocioActivo() {
        when(negocioActual.findNegocioId()).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.generar(filtro(false, 1, 1)))
                .isInstanceOf(MayorException.class)
                .extracting("codigo")
                .isEqualTo("MAYOR_SIN_NEGOCIO");
    }

    @Test
    void fallaSiLaCuentaNoExisteEnElNegocio() {
        when(negocioActual.findNegocioId()).thenReturn(Optional.of(54));
        when(cuentaRepository.findByNumeroCuenta(21201001L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.generar(filtro(false, 1, 1)))
                .isInstanceOf(MayorException.class)
                .extracting("codigo")
                .isEqualTo("MAYOR_CUENTA_NO_EXISTE");
    }

    @Test
    void fallaSiLaCuentaEsDeOtroNegocio() {
        when(negocioActual.findNegocioId()).thenReturn(Optional.of(54));
        when(cuentaRepository.findByNumeroCuenta(21201001L))
                .thenReturn(Optional.of(cuenta(56)));

        assertThatThrownBy(() -> useCase.generar(filtro(false, 1, 1)))
                .isInstanceOf(MayorException.class)
                .extracting("codigo")
                .isEqualTo("MAYOR_CUENTA_NO_EXISTE");
    }

    @Test
    void noPideSaldoAnteriorSiSeMarca() {
        when(negocioActual.findNegocioId()).thenReturn(Optional.of(54));
        when(cuentaRepository.findByNumeroCuenta(21201001L))
                .thenReturn(Optional.of(cuenta(54)));
        when(mayorMovimientoPort.findDetalle(54, filtro(true, 1, 1)))
                .thenReturn(List.of());
        when(mayorControlPort.findControl(54, filtro(true, 1, 1)))
                .thenReturn(MayorControl.vacio());

        MayorCuenta mayor = useCase.generar(filtro(true, 1, 1));

        assertThat(mayor.saldoAnterior()).isEqualByComparingTo("0");
        assertThat(mayor.saldoFinal()).isEqualByComparingTo("0");
        verify(mayorMovimientoPort, never()).findSaldoAnterior(anyInt(), any());
    }

    @Test
    void pideTasasOnDemandYFallaSiSiguenFaltando() {
        when(negocioActual.findNegocioId()).thenReturn(Optional.of(54));
        when(cuentaRepository.findByNumeroCuenta(21201001L))
                .thenReturn(Optional.of(cuenta(54)));
        when(mayorMovimientoPort.findFechasSinCotizacion(54, filtro(false, 1, 2)))
                .thenReturn(List.of(DIA));

        assertThatThrownBy(() -> useCase.generar(filtro(false, 1, 2)))
                .isInstanceOf(MayorException.class)
                .extracting("codigo")
                .isEqualTo("MAYOR_COTIZACION_FALTANTE");
        verify(cotizacionOnDemand).asegurar(anyInt(), any(), any(), any(), any(), any());
        verify(mayorMovimientoPort, never()).findDetalle(anyInt(), any());
    }

    @Test
    void traduceMonedaInvalidaDelServicioDeCotizaciones() {
        when(negocioActual.findNegocioId()).thenReturn(Optional.of(54));
        when(cuentaRepository.findByNumeroCuenta(21201001L))
                .thenReturn(Optional.of(cuenta(54)));
        when(mayorMovimientoPort.findFechasSinCotizacion(54, filtro(false, 1, 2)))
                .thenReturn(List.of(DIA));
        doThrow(new MonedaException("MONEDA_INVALIDA", "Moneda inexistente"))
                .when(cotizacionOnDemand)
                .asegurar(anyInt(), any(), any(), any(), any(), any());

        assertThatThrownBy(() -> useCase.generar(filtro(false, 1, 2)))
                .isInstanceOf(MayorException.class)
                .extracting("codigo")
                .isEqualTo("MAYOR_MONEDA_INVALIDA");
    }

    @Test
    void calculaConSaldoAnteriorYControl() {
        MayorControl control = new MayorControl(
                1, 0, List.of(), List.of());
        MayorDetalle detalle = new MayorDetalle(
                DIA, 1, "Factura", "Concepto", 42,
                new BigDecimal("10.00"), BigDecimal.ZERO, 0L, "");

        when(negocioActual.findNegocioId()).thenReturn(Optional.of(54));
        when(cuentaRepository.findByNumeroCuenta(21201001L))
                .thenReturn(Optional.of(cuenta(54)));
        when(mayorMovimientoPort.findSaldoAnterior(54, filtro(false, 1, 1)))
                .thenReturn(new BigDecimal("100.00"));
        when(mayorMovimientoPort.findDetalle(54, filtro(false, 1, 1)))
                .thenReturn(List.of(detalle));
        when(mayorControlPort.findControl(54, filtro(false, 1, 1)))
                .thenReturn(control);

        MayorCuenta mayor = useCase.generar(filtro(false, 1, 1));

        assertThat(mayor.saldoAnterior()).isEqualByComparingTo("100.00");
        assertThat(mayor.saldoFinal()).isEqualByComparingTo("110.00");
        assertThat(mayor.control()).isSameAs(control);
    }

    private static MayorFiltro filtro(boolean sinSaldo, int origen, int destino) {
        return new MayorFiltro(21201001L, DIA, DIA, sinSaldo, origen, destino);
    }

    private static Cuenta cuenta(int negocioId) {
        return Cuenta.builder()
                .numeroCuenta(21201001L)
                .nombre("IVA Débito Fiscal")
                .negocioId(negocioId)
                .build();
    }

}
