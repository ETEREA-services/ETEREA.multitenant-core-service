package eterea.tenant.service.modules.contable.mayor.application.usecases;

import eterea.tenant.service.modules.contable.cuenta.domain.model.Cuenta;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.out.CuentaRepository;
import eterea.tenant.service.modules.contable.mayor.application.service.MayorCalculator;
import eterea.tenant.service.modules.contable.mayor.domain.exception.MayorException;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorControl;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorDiscriminado;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorDiscriminadoFila;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorDiscriminadoFiltro;
import eterea.tenant.service.modules.contable.mayor.domain.ports.out.MayorControlPort;
import eterea.tenant.service.modules.contable.mayor.domain.ports.out.MayorMovimientoPort;
import eterea.tenant.service.modules.contable.moneda.domain.ports.out.CotizacionOnDemandPort;
import eterea.tenant.service.modules.platform.api.NegocioActual;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenerarMayorDiscriminadoUseCaseImplTest {

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

    private GenerarMayorDiscriminadoUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new GenerarMayorDiscriminadoUseCaseImpl(
                cuentaRepository,
                mayorMovimientoPort,
                mayorControlPort,
                cotizacionOnDemand,
                new MayorCalculator(),
                negocioActual);
    }

    @Test
    void advierteCuentaInexistenteYConsultaSoloLasValidas() {
        when(negocioActual.findNegocioId()).thenReturn(Optional.of(54));
        when(cuentaRepository.findAllByNegocioIdOrdered(54))
                .thenReturn(List.of(cuenta(1L, "Caja")));
        when(mayorMovimientoPort.findDetalleDiscriminado(eq(54), any()))
                .thenReturn(List.of(fila(1L)));
        when(mayorControlPort.findControlDiscriminado(eq(54), any()))
                .thenReturn(MayorControl.vacio());

        MayorDiscriminado resultado = useCase.generar(
                new MayorDiscriminadoFiltro(List.of(1L, 999L), DIA, DIA, 1, 1));

        assertThat(resultado.filas()).hasSize(1);
        assertThat(resultado.advertencias())
                .anyMatch(advertencia -> advertencia.contains("999"));

        ArgumentCaptor<MayorDiscriminadoFiltro> captor =
                ArgumentCaptor.forClass(MayorDiscriminadoFiltro.class);
        verify(mayorMovimientoPort).findDetalleDiscriminado(eq(54), captor.capture());
        assertThat(captor.getValue().cuentas()).containsExactly(1L);
    }

    @Test
    void advierteMovimientosExcluidosPorTipoFaltante() {
        when(negocioActual.findNegocioId()).thenReturn(Optional.of(54));
        when(cuentaRepository.findAllByNegocioIdOrdered(54))
                .thenReturn(List.of(cuenta(1L, "Caja")));
        when(mayorMovimientoPort.findDetalleDiscriminado(eq(54), any()))
                .thenReturn(List.of(fila(1L)));
        when(mayorControlPort.findControlDiscriminado(eq(54), any()))
                .thenReturn(new MayorControl(5, 4, List.of(), List.of(141)));

        MayorDiscriminado resultado = useCase.generar(
                new MayorDiscriminadoFiltro(List.of(1L), DIA, DIA, 1, 1));

        assertThat(resultado.advertencias())
                .anyMatch(advertencia -> advertencia.contains("Se excluyeron 4")
                        && advertencia.contains("141"));
    }

    @Test
    void devuelveVacioSiNingunaCuentaExiste() {
        when(negocioActual.findNegocioId()).thenReturn(Optional.of(54));
        when(cuentaRepository.findAllByNegocioIdOrdered(54)).thenReturn(List.of());

        MayorDiscriminado resultado = useCase.generar(
                new MayorDiscriminadoFiltro(List.of(999L), DIA, DIA, 1, 1));

        assertThat(resultado.filas()).isEmpty();
        assertThat(resultado.advertencias())
                .anyMatch(advertencia -> advertencia.contains("999"));
        verify(mayorMovimientoPort, never()).findDetalleDiscriminado(anyInt(), any());
    }

    @Test
    void pideTasasOnDemandYFallaSiSiguenFaltando() {
        when(negocioActual.findNegocioId()).thenReturn(Optional.of(54));
        when(cuentaRepository.findAllByNegocioIdOrdered(54))
                .thenReturn(List.of(cuenta(1L, "Caja")));
        when(mayorMovimientoPort.findFechasSinCotizacionDiscriminado(eq(54), any()))
                .thenReturn(List.of(DIA));

        assertThatThrownBy(() -> useCase.generar(
                new MayorDiscriminadoFiltro(List.of(1L), DIA, DIA, 1, 2)))
                .isInstanceOf(MayorException.class)
                .extracting("codigo")
                .isEqualTo("MAYOR_COTIZACION_FALTANTE");
        verify(cotizacionOnDemand).asegurar(anyInt(), any(), any(), any(), any(), any());
    }

    private static Cuenta cuenta(Long numeroCuenta, String nombre) {
        return Cuenta.builder()
                .numeroCuenta(numeroCuenta)
                .nombre(nombre)
                .negocioId(54)
                .build();
    }

    private static MayorDiscriminadoFila fila(Long cuenta) {
        return new MayorDiscriminadoFila(
                DIA,
                cuenta,
                "Caja",
                "Factura",
                "Concepto",
                42,
                new BigDecimal("10.00"),
                BigDecimal.ZERO,
                "");
    }

}
