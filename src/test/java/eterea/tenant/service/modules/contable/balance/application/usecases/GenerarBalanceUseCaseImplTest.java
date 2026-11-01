package eterea.tenant.service.modules.contable.balance.application.usecases;

import eterea.tenant.service.modules.contable.balance.application.service.BalanceCache;
import eterea.tenant.service.modules.contable.balance.application.service.BalanceCalculator;
import eterea.tenant.service.modules.contable.balance.domain.exception.BalanceException;
import eterea.tenant.service.modules.contable.balance.domain.model.Balance;
import eterea.tenant.service.modules.contable.balance.domain.model.BalanceFiltro;
import eterea.tenant.service.modules.contable.balance.domain.model.BalanceModo;
import eterea.tenant.service.modules.contable.balance.domain.model.BalanceTotales;
import eterea.tenant.service.modules.contable.balance.domain.ports.out.BalanceMovimientoPort;
import eterea.tenant.service.modules.contable.balance.domain.ports.out.CotizacionOnDemandPort;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.out.CuentaRepository;
import eterea.tenant.service.modules.platform.api.NegocioActual;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenerarBalanceUseCaseImplTest {

    private static final LocalDate DIA = LocalDate.of(2026, 10, 31);

    @Mock
    private CuentaRepository cuentaRepository;
    @Mock
    private BalanceMovimientoPort balanceMovimientoPort;
    @Mock
    private CotizacionOnDemandPort cotizacionOnDemand;
    @Mock
    private BalanceCalculator balanceCalculator;
    @Mock
    private BalanceCache balanceCache;
    @Mock
    private NegocioActual negocioActual;

    @InjectMocks
    private GenerarBalanceUseCaseImpl useCase;

    @Test
    void fallaSinNegocioActivo() {
        when(negocioActual.findNegocioId()).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.generar(filtro(false)))
                .isInstanceOf(BalanceException.class)
                .extracting("codigo")
                .isEqualTo("BALANCE_SIN_NEGOCIO");
    }

    @Test
    void pideLasTasasOnDemandYFallaSiSiguenFaltando() {
        when(negocioActual.findNegocioId()).thenReturn(Optional.of(54));
        when(balanceMovimientoPort.findFechasSinCotizacion(anyInt(), any()))
                .thenReturn(List.of(DIA));

        assertThatThrownBy(() -> useCase.generar(filtro(false)))
                .isInstanceOf(BalanceException.class)
                .extracting("codigo")
                .isEqualTo("BALANCE_COTIZACION_FALTANTE");
        verify(cotizacionOnDemand).asegurar(anyInt(), any(), any(), any(), any(), any());
        verify(balanceMovimientoPort, never()).findSaldos(anyInt(), any());
    }

    @Test
    void devuelveLaCacheSinRecalcular() {
        Balance cacheado = balance();
        when(negocioActual.findNegocioId()).thenReturn(Optional.of(54));
        when(balanceMovimientoPort.findFechasSinCotizacion(anyInt(), any()))
                .thenReturn(List.of());
        when(balanceMovimientoPort.watermark(anyInt(), any())).thenReturn("w");
        when(balanceCache.get(anyString())).thenReturn(cacheado);

        assertThat(useCase.generar(filtro(false))).isSameAs(cacheado);
        verify(balanceMovimientoPort, never()).findSaldos(anyInt(), any());
    }

    @Test
    void recalculaDespuesDeObtenerLasTasas() {
        Balance calculado = balance();
        when(negocioActual.findNegocioId()).thenReturn(Optional.of(54));
        when(balanceMovimientoPort.findFechasSinCotizacion(anyInt(), any()))
                .thenReturn(List.of(DIA), List.of());
        when(balanceMovimientoPort.watermark(anyInt(), any())).thenReturn("w");
        when(balanceCache.get(anyString())).thenReturn(null);
        when(cuentaRepository.findAllByNegocioIdOrdered(54)).thenReturn(List.of());
        when(balanceMovimientoPort.findSaldos(anyInt(), any())).thenReturn(List.of());
        when(balanceCalculator.calcular(any(), any(), any(), any())).thenReturn(calculado);

        assertThat(useCase.generar(filtro(false))).isSameAs(calculado);
        verify(cotizacionOnDemand).asegurar(anyInt(), any(), any(), any(), any(), any());
    }

    @Test
    void calculaYGuardaEnCache() {
        Balance calculado = balance();
        when(negocioActual.findNegocioId()).thenReturn(Optional.of(54));
        when(balanceMovimientoPort.watermark(anyInt(), any())).thenReturn("w");
        when(balanceCache.get(anyString())).thenReturn(null);
        when(balanceMovimientoPort.findFechasSinCotizacion(anyInt(), any())).thenReturn(List.of());
        when(cuentaRepository.findAllByNegocioIdOrdered(54)).thenReturn(List.of());
        when(balanceMovimientoPort.findSaldos(anyInt(), any())).thenReturn(List.of());
        when(balanceMovimientoPort.findSaldosApertura(anyInt(), any())).thenReturn(List.of());
        when(balanceCalculator.calcular(any(), any(), any(), any())).thenReturn(calculado);

        assertThat(useCase.generar(filtro(true))).isSameAs(calculado);
        verify(balanceMovimientoPort).findSaldosApertura(anyInt(), any());
        verify(balanceCache).put(anyString(), any());
    }

    private static BalanceFiltro filtro(boolean apertura) {
        return new BalanceFiltro(
                DIA, DIA, apertura, true, BalanceModo.ESTRUCTURA, 1, 1);
    }

    private static Balance balance() {
        return new Balance(
                List.of(),
                new BalanceTotales(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO),
                filtro(false),
                List.of());
    }

}
