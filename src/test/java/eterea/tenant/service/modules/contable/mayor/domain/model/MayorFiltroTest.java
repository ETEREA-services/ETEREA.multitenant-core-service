package eterea.tenant.service.modules.contable.mayor.domain.model;

import eterea.tenant.service.modules.contable.mayor.domain.exception.MayorException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MayorFiltroTest {

    private static final LocalDate DIA = LocalDate.of(2026, 11, 11);

    @Test
    void completaLaMonedaBaseYCalculaLaFechaDeApertura() {
        MayorFiltro filtro = new MayorFiltro(21201001L, DIA, DIA, false, null, null);

        assertThat(filtro.monedaRegistroId()).isEqualTo(1);
        assertThat(filtro.monedaExpresionId()).isEqualTo(1);
        assertThat(filtro.esMonedaBase()).isTrue();
        assertThat(filtro.fechaApertura()).isEqualTo(DIA.minusDays(1));
    }

    @Test
    void rechazaRangoInvertido() {
        assertThatThrownBy(() -> new MayorFiltro(
                21201001L, DIA, DIA.minusDays(1), false, 1, 1))
                .isInstanceOf(MayorException.class)
                .extracting("codigo")
                .isEqualTo("MAYOR_RANGO_INVALIDO");
    }

    @Test
    void rechazaCuentaVacia() {
        assertThatThrownBy(() -> new MayorFiltro(0, DIA, DIA, false, 1, 1))
                .isInstanceOf(MayorException.class)
                .extracting("codigo")
                .isEqualTo("MAYOR_CUENTA_REQUERIDA");
    }

    @Test
    void rechazaMonedaInvalida() {
        assertThatThrownBy(() -> new MayorFiltro(21201001L, DIA, DIA, false, 0, 1))
                .isInstanceOf(MayorException.class)
                .extracting("codigo")
                .isEqualTo("MAYOR_MONEDA_INVALIDA");
    }

    @Test
    void discriminadoQuitaDuplicadosYRechazaListaVacia() {
        MayorDiscriminadoFiltro filtro = new MayorDiscriminadoFiltro(
                List.of(1L, 2L, 2L), DIA, DIA, null, null);

        assertThat(filtro.cuentas()).containsExactly(1L, 2L);
        assertThatThrownBy(() -> new MayorDiscriminadoFiltro(
                List.of(), DIA, DIA, 1, 1))
                .isInstanceOf(MayorException.class)
                .extracting("codigo")
                .isEqualTo("MAYOR_CUENTAS_REQUERIDAS");
    }

}
