package eterea.tenant.service.modules.contable.cuenta.application.usecases;

import eterea.tenant.service.modules.contable.cuenta.domain.exception.CuentaInvalidaException;
import eterea.tenant.service.modules.contable.cuenta.domain.model.Cuenta;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.out.CuentaMovimientoPort;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.out.CuentaRepository;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.out.NegocioActualPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateCuentaUseCaseImplTest {

    @Mock
    private CuentaRepository repository;

    @Mock
    private CuentaMovimientoPort movimientoPort;

    @Mock
    private NegocioActualPort negocioActualPort;

    private CreateCuentaUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new CreateCuentaUseCaseImpl(repository, movimientoPort, negocioActualPort);
    }

    @Test
    void creaUnaCuentaYMarcaLosAncestrosComoIntegradores() {
        Cuenta nueva = cuenta(11_101_001L, "CAJA MONEDA NACIONAL");
        nueva.setTransfiere((byte) 1);
        nueva.setVentas((byte) 1);

        when(repository.existsByNumeroCuenta(11_101_001L)).thenReturn(false);
        when(repository.findByNumeroCuenta(11_101_000L)).thenReturn(Optional.of(cuenta(11_101_000L, "CAJA")));
        when(repository.findByNumeroCuenta(11_100_000L)).thenReturn(Optional.of(cuenta(11_100_000L, "CAJA Y BANCOS")));
        when(repository.findByNumeroCuenta(11_000_000L)).thenReturn(Optional.of(cuenta(11_000_000L, "ACTIVO CORRIENTE")));
        when(repository.findByNumeroCuenta(10_000_000L)).thenReturn(Optional.of(cuenta(10_000_000L, "ACTIVO")));
        when(negocioActualPort.findNegocioId()).thenReturn(Optional.of(54));
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Cuenta resultado = useCase.create(nueva);

        assertThat(resultado.getNumeroCuenta()).isEqualTo(11_101_001L);
        assertThat(resultado.getNombre()).isEqualTo("CAJA MONEDA NACIONAL");
        assertThat(resultado.getNegocioId()).isEqualTo(54);
        assertThat(resultado.getGrado()).isEqualTo(5);
        assertThat(resultado.getGrado4()).isEqualTo(11_101_000L);
        assertThat(resultado.getIntegra()).isZero();
        assertThat(resultado.getTransfiere()).isEqualTo((byte) 1);
        assertThat(resultado.getVentas()).isEqualTo((byte) 1);

        ArgumentCaptor<Cuenta> captor = ArgumentCaptor.forClass(Cuenta.class);
        verify(repository, org.mockito.Mockito.times(5)).save(captor.capture());
        assertThat(captor.getAllValues())
                .filteredOn(cuenta -> !cuenta.getNumeroCuenta().equals(11_101_001L))
                .allMatch(cuenta -> cuenta.getIntegra() == 1);
    }

    @Test
    void rechazaUnaCuentaDuplicada() {
        when(repository.existsByNumeroCuenta(11_101_001L)).thenReturn(true);

        assertThatThrownBy(() -> useCase.create(cuenta(11_101_001L, "CAJA")))
                .isInstanceOfSatisfying(CuentaInvalidaException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("CUENTA_YA_EXISTE"));

        verify(repository, never()).save(any());
    }

    @Test
    void rechazaSiFaltaUnAncestro() {
        when(repository.existsByNumeroCuenta(11_101_001L)).thenReturn(false);
        when(repository.findByNumeroCuenta(11_101_000L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.create(cuenta(11_101_001L, "CAJA")))
                .isInstanceOfSatisfying(CuentaInvalidaException.class, ex -> {
                    assertThat(ex.getCodigo()).isEqualTo("CUENTA_PADRE_FALTANTE");
                    assertThat(ex).hasMessageContaining("11101000");
                });

        verify(repository, never()).save(any());
    }

    @Test
    void rechazaSiUnAncestroTieneMovimientos() {
        when(repository.existsByNumeroCuenta(11_101_001L)).thenReturn(false);
        when(repository.findByNumeroCuenta(11_101_000L))
                .thenReturn(Optional.of(cuenta(11_101_000L, "CAJA")));
        when(movimientoPort.existeMovimientoEnCuenta(11_101_000L)).thenReturn(true);

        assertThatThrownBy(() -> useCase.create(cuenta(11_101_001L, "CAJA")))
                .isInstanceOfSatisfying(CuentaInvalidaException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("CUENTA_PADRE_CON_MOVIMIENTOS"));

        verify(repository, never()).save(any());
    }

    @Test
    void validaElNombreAntesDeConsultarLaBase() {
        assertThatThrownBy(() -> useCase.create(cuenta(11_101_001L, "   ")))
                .isInstanceOfSatisfying(CuentaInvalidaException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("CUENTA_NOMBRE_REQUERIDO"));

        verify(repository, never()).existsByNumeroCuenta(any());
    }

    @Test
    void validaLaCuentaMaestro() {
        Cuenta nueva = cuenta(11_101_001L, "CAJA");
        nueva.setCuentaMaestro(new BigDecimal("1.5"));

        assertThatThrownBy(() -> useCase.create(nueva))
                .isInstanceOfSatisfying(CuentaInvalidaException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("CUENTA_MAESTRO_INVALIDA"));
    }

    @Test
    void usaElPaddingParaValidarLosAncestrosDeUnCodigoCorto() {
        Cuenta nueva = cuenta(1_100L, "CUENTA CORTA");
        when(repository.existsByNumeroCuenta(1_100L)).thenReturn(false);
        when(repository.findByNumeroCuenta(11_000_000L))
                .thenReturn(Optional.of(cuenta(11_000_000L, "ACTIVO CORRIENTE")));
        when(repository.findByNumeroCuenta(10_000_000L))
                .thenReturn(Optional.of(cuenta(10_000_000L, "ACTIVO")));
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Cuenta resultado = useCase.create(nueva);

        assertThat(resultado.getNumeroCuenta()).isEqualTo(1_100L);
        assertThat(resultado.getGrado()).isEqualTo(5);
        verify(repository, org.mockito.Mockito.times(2)).findByNumeroCuenta(11_000_000L);
        verify(repository, org.mockito.Mockito.times(2)).findByNumeroCuenta(10_000_000L);
    }

    private static Cuenta cuenta(long numeroCuenta, String nombre) {
        return Cuenta.builder()
                .numeroCuenta(numeroCuenta)
                .nombre(nombre)
                .negocioId(1)
                .cuentaMaestro(BigDecimal.ZERO)
                .build();
    }
}
