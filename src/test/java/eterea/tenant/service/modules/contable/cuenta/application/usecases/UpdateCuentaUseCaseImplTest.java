package eterea.tenant.service.modules.contable.cuenta.application.usecases;

import eterea.tenant.service.modules.contable.cuenta.domain.exception.CuentaInvalidaException;
import eterea.tenant.service.modules.contable.cuenta.domain.model.Cuenta;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.out.CuentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
class UpdateCuentaUseCaseImplTest {

    @Mock
    private CuentaRepository repository;

    private UpdateCuentaUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new UpdateCuentaUseCaseImpl(repository);
    }

    @Test
    void actualizaNombreCuentaMaestroYFlagsSinCambiarElCodigo() {
        Cuenta existente = Cuenta.builder()
                .numeroCuenta(11_101_001L)
                .nombre("ANTERIOR")
                .negocioId(1)
                .integra((byte) 0)
                .grado(5)
                .cuentaMaestro(BigDecimal.ZERO)
                .build();
        Cuenta cambios = Cuenta.builder()
                .nombre("NUEVO NOMBRE")
                .cuentaMaestro(new BigDecimal("123"))
                .transfiere((byte) 1)
                .movimientoStock((byte) 1)
                .ventas((byte) 1)
                .compras((byte) 1)
                .gastos((byte) 1)
                .build();

        when(repository.findByNumeroCuenta(11_101_001L)).thenReturn(Optional.of(existente));
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Cuenta resultado = useCase.update(11_101_001L, cambios);

        assertThat(resultado.getNumeroCuenta()).isEqualTo(11_101_001L);
        assertThat(resultado.getNombre()).isEqualTo("NUEVO NOMBRE");
        assertThat(resultado.getCuentaMaestro()).isEqualByComparingTo("123");
        assertThat(resultado.getTransfiere()).isEqualTo((byte) 1);
        assertThat(resultado.getMovimientoStock()).isEqualTo((byte) 1);
        assertThat(resultado.getVentas()).isEqualTo((byte) 1);
        assertThat(resultado.getCompras()).isEqualTo((byte) 1);
        assertThat(resultado.getGastos()).isEqualTo((byte) 1);
        assertThat(resultado.getGrado()).isEqualTo(5);
    }

    @Test
    void rechazaUnaCuentaInexistente() {
        when(repository.findByNumeroCuenta(11_101_001L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.update(11_101_001L, Cuenta.builder().nombre("X").build()))
                .isInstanceOfSatisfying(CuentaInvalidaException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("CUENTA_NO_EXISTE"));

        verify(repository, never()).save(any());
    }

    @Test
    void rechazaElCambioDeCodigo() {
        when(repository.findByNumeroCuenta(11_101_001L))
                .thenReturn(Optional.of(Cuenta.builder().numeroCuenta(11_101_001L).build()));
        Cuenta cambios = Cuenta.builder()
                .numeroCuenta(11_101_002L)
                .nombre("X")
                .build();

        assertThatThrownBy(() -> useCase.update(11_101_001L, cambios))
                .isInstanceOfSatisfying(CuentaInvalidaException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("CUENTA_CODIGO_INMUTABLE"));

        verify(repository, never()).save(any());
    }

    @Test
    void validaElNombre() {
        when(repository.findByNumeroCuenta(11_101_001L))
                .thenReturn(Optional.of(Cuenta.builder().numeroCuenta(11_101_001L).build()));

        assertThatThrownBy(() -> useCase.update(11_101_001L, Cuenta.builder().nombre(" ").build()))
                .isInstanceOfSatisfying(CuentaInvalidaException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("CUENTA_NOMBRE_REQUERIDO"));
    }
}
