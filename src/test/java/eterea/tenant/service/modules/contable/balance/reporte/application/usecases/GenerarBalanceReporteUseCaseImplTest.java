package eterea.tenant.service.modules.contable.balance.reporte.application.usecases;

import eterea.tenant.service.modules.contable.balance.domain.exception.BalanceException;
import eterea.tenant.service.modules.contable.balance.domain.model.Balance;
import eterea.tenant.service.modules.contable.balance.domain.model.BalanceCuenta;
import eterea.tenant.service.modules.contable.balance.domain.model.BalanceFiltro;
import eterea.tenant.service.modules.contable.balance.domain.model.BalanceModo;
import eterea.tenant.service.modules.contable.balance.domain.model.BalanceTotales;
import eterea.tenant.service.modules.contable.balance.domain.model.MonedaInfo;
import eterea.tenant.service.modules.contable.balance.domain.ports.in.GenerarBalanceUseCase;
import eterea.tenant.service.modules.contable.balance.domain.ports.out.MonedaQueryPort;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenerarBalanceReporteUseCaseImplTest {

    private static final LocalDate DIA = LocalDate.of(2026, 10, 31);

    @Mock
    private GenerarBalanceUseCase generarBalance;
    @Mock
    private NegocioActual negocioActual;
    @Mock
    private MonedaQueryPort monedaQuery;

    @InjectMocks
    private GenerarBalanceReporteUseCaseImpl useCase;

    @Test
    void armaElBalanceGeneralConTotalesYDiferencia() {
        when(generarBalance.generar(any())).thenReturn(balance(BalanceModo.ESTRUCTURA));
        when(negocioActual.findActual()).thenReturn(Optional.of(new NegocioActualInfo(54, "TSA Agencia")));
        when(monedaQuery.findById(1)).thenReturn(Optional.of(new MonedaInfo(1, "Peso", "ARS", true)));
        when(monedaQuery.findById(2)).thenReturn(Optional.of(new MonedaInfo(2, "Dólar", "USD", false)));

        ReporteTabla tabla = useCase.generar(filtro(BalanceModo.ESTRUCTURA, 1, 2));

        assertThat(tabla.columnas()).hasSize(5);
        assertThat(tabla.cabecera().titulo()).isEqualTo("Balance General");
        assertThat(tabla.cabecera().subtitulo())
                .isEqualTo("Período: 31-10-2026 - 31-10-2026 · Registrado en ARS · Expresado en USD");
        assertThat(tabla.advertencias()).hasSize(1);

        assertThat(tabla.filas().getFirst().celdas())
                .containsExactly("10000000", "ACTIVO", "150,00", "0,00", "");
        assertThat(tabla.filas().getLast().celdas())
                .containsExactly("", "TOTALES", "150,00", "150,46", "(0,46)");
    }

    @Test
    void armaElPorCuentaConSaldoFirmado() {
        when(generarBalance.generar(any())).thenReturn(balance(BalanceModo.POR_CUENTA));
        when(negocioActual.findActual()).thenReturn(Optional.of(new NegocioActualInfo(54, "TSA Agencia")));
        when(monedaQuery.findById(1)).thenReturn(Optional.of(new MonedaInfo(1, "Peso", "ARS", true)));

        ReporteTabla tabla = useCase.generar(filtro(BalanceModo.POR_CUENTA, 1, 1));

        assertThat(tabla.columnas()).hasSize(3);
        assertThat(tabla.cabecera().titulo()).isEqualTo("Saldos Contables");
        assertThat(tabla.filas().getFirst().celdas())
                .containsExactly("10000000", "ACTIVO", "150,00");
        assertThat(tabla.filas().getLast().celdas())
                .containsExactly("", "TOTAL", "(0,46)");
    }

    @Test
    void fallaSiLaMonedaNoExiste() {
        when(generarBalance.generar(any())).thenReturn(balance(BalanceModo.ESTRUCTURA));
        when(negocioActual.findActual()).thenReturn(Optional.of(new NegocioActualInfo(54, "TSA Agencia")));
        when(monedaQuery.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.generar(filtro(BalanceModo.ESTRUCTURA, 99, 99)))
                .isInstanceOf(BalanceException.class)
                .extracting("codigo")
                .isEqualTo("BALANCE_MONEDA_INVALIDA");
    }

    private static BalanceFiltro filtro(BalanceModo modo, int registro, int expresion) {
        return new BalanceFiltro(DIA, DIA, false, true, modo, registro, expresion);
    }

    private static Balance balance(BalanceModo modo) {
        BigDecimal deudores = new BigDecimal("150.00");
        BigDecimal acreedores = new BigDecimal("150.46");
        return new Balance(
                List.of(
                        new BalanceCuenta(10000000L, "ACTIVO", 1, true,
                                deudores, BigDecimal.ZERO, deudores, BigDecimal.ZERO),
                        new BalanceCuenta(20000000L, "PASIVO", 1, true,
                                BigDecimal.ZERO, acreedores, BigDecimal.ZERO, acreedores)),
                new BalanceTotales(deudores, acreedores, new BigDecimal("-0.46")),
                filtro(modo, 1, 1),
                List.of("El balance no cuadra: diferencia de -0.46."));
    }

}
