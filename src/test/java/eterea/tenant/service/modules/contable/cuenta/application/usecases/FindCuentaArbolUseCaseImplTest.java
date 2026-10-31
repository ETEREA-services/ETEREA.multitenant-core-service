package eterea.tenant.service.modules.contable.cuenta.application.usecases;

import eterea.tenant.service.modules.contable.cuenta.domain.model.Cuenta;
import eterea.tenant.service.modules.contable.cuenta.domain.model.CuentaNodo;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.out.CuentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindCuentaArbolUseCaseImplTest {

    @Mock
    private CuentaRepository repository;

    private FindCuentaArbolUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new FindCuentaArbolUseCaseImpl(repository);
    }

    @Test
    void armaElArbolYMarcaLasCuentasHuerfanas() {
        when(repository.findAllOrdered()).thenReturn(List.of(
                cuenta(10_000_000L),
                cuenta(11_000_000L),
                cuenta(11_100_000L),
                cuenta(11_101_000L),
                cuenta(11_101_001L),
                cuenta(12_000_000L),
                cuenta(99_999_999L)
        ));

        List<CuentaNodo> arbol = useCase.findArbol();

        assertThat(arbol).hasSize(2);

        CuentaNodo activo = arbol.get(0);
        assertThat(activo.getCuenta().getNumeroCuenta()).isEqualTo(10_000_000L);
        assertThat(activo.isHuerfana()).isFalse();
        assertThat(activo.getHijos())
                .extracting(nodo -> nodo.getCuenta().getNumeroCuenta())
                .containsExactly(11_000_000L, 12_000_000L);

        CuentaNodo activoCorriente = activo.getHijos().get(0);
        assertThat(activoCorriente.getHijos())
                .extracting(nodo -> nodo.getCuenta().getNumeroCuenta())
                .containsExactly(11_100_000L);

        CuentaNodo cajaYBancos = activoCorriente.getHijos().get(0);
        assertThat(cajaYBancos.getHijos())
                .extracting(nodo -> nodo.getCuenta().getNumeroCuenta())
                .containsExactly(11_101_000L);

        CuentaNodo caja = cajaYBancos.getHijos().get(0);
        assertThat(caja.getHijos())
                .extracting(nodo -> nodo.getCuenta().getNumeroCuenta())
                .containsExactly(11_101_001L);

        CuentaNodo huerfana = arbol.get(1);
        assertThat(huerfana.getCuenta().getNumeroCuenta()).isEqualTo(99_999_999L);
        assertThat(huerfana.isHuerfana()).isTrue();
        assertThat(huerfana.getHijos()).isEmpty();
    }

    @Test
    void devuelveUnaListaVaciaSiNoHayCuentas() {
        when(repository.findAllOrdered()).thenReturn(List.of());

        assertThat(useCase.findArbol()).isEmpty();
    }

    private static Cuenta cuenta(long numeroCuenta) {
        return Cuenta.builder()
                .numeroCuenta(numeroCuenta)
                .nombre("CUENTA " + numeroCuenta)
                .build();
    }
}
