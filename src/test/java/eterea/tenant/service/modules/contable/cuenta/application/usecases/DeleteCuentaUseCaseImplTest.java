package eterea.tenant.service.modules.contable.cuenta.application.usecases;

import eterea.tenant.service.modules.contable.cuenta.domain.exception.CuentaInvalidaException;
import eterea.tenant.service.modules.contable.cuenta.domain.model.Cuenta;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.out.CuentaMovimientoPort;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.out.CuentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteCuentaUseCaseImplTest {

    @Mock
    private CuentaRepository repository;

    @Mock
    private CuentaMovimientoPort movimientoPort;

    private DeleteCuentaUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new DeleteCuentaUseCaseImpl(repository, movimientoPort);
    }

    @Test
    void eliminaUnaHojaSinMovimientos() {
        when(repository.findByNumeroCuenta(11_101_001L))
                .thenReturn(Optional.of(Cuenta.builder().numeroCuenta(11_101_001L).build()));
        when(repository.existsByNumeroCuentaGreaterThanAndNumeroCuentaLessThanEqual(
                11_101_001L, 11_101_001L)).thenReturn(false);
        when(movimientoPort.existeMovimientoEnRango(11_101_001L, 11_101_001L)).thenReturn(false);

        useCase.delete(11_101_001L);

        verify(repository).deleteByNumeroCuenta(11_101_001L);
    }

    @Test
    void rechazaUnaCuentaInexistente() {
        when(repository.findByNumeroCuenta(11_101_001L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.delete(11_101_001L))
                .isInstanceOfSatisfying(CuentaInvalidaException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("CUENTA_NO_EXISTE"));

        verify(repository, never()).deleteByNumeroCuenta(any());
    }

    @Test
    void rechazaUnaCuentaConHijas() {
        when(repository.findByNumeroCuenta(11_101_000L))
                .thenReturn(Optional.of(Cuenta.builder().numeroCuenta(11_101_000L).build()));
        when(repository.existsByNumeroCuentaGreaterThanAndNumeroCuentaLessThanEqual(
                11_101_000L, 11_101_999L)).thenReturn(true);

        assertThatThrownBy(() -> useCase.delete(11_101_000L))
                .isInstanceOfSatisfying(CuentaInvalidaException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("CUENTA_CON_HIJOS"));

        verify(repository, never()).deleteByNumeroCuenta(any());
    }

    @Test
    void rechazaUnaCuentaConMovimientos() {
        when(repository.findByNumeroCuenta(11_101_001L))
                .thenReturn(Optional.of(Cuenta.builder().numeroCuenta(11_101_001L).build()));
        when(repository.existsByNumeroCuentaGreaterThanAndNumeroCuentaLessThanEqual(
                11_101_001L, 11_101_001L)).thenReturn(false);
        when(movimientoPort.existeMovimientoEnRango(11_101_001L, 11_101_001L)).thenReturn(true);

        assertThatThrownBy(() -> useCase.delete(11_101_001L))
                .isInstanceOfSatisfying(CuentaInvalidaException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("CUENTA_CON_MOVIMIENTOS"));

        verify(repository, never()).deleteByNumeroCuenta(any());
    }
}
