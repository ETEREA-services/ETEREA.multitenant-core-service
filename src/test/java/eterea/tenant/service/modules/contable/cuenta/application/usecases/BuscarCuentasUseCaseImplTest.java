package eterea.tenant.service.modules.contable.cuenta.application.usecases;

import eterea.tenant.service.modules.contable.cuenta.domain.exception.CuentaInvalidaException;
import eterea.tenant.service.modules.contable.cuenta.domain.model.Cuenta;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.out.CuentaRepository;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.out.NegocioActualPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarCuentasUseCaseImplTest {

    @Mock
    private CuentaRepository cuentaRepository;
    @Mock
    private NegocioActualPort negocioActualPort;

    @InjectMocks
    private BuscarCuentasUseCaseImpl useCase;

    @Test
    void buscaConElNegocioActivoYElTextoRecortado() {
        Cuenta cuenta = Cuenta.builder()
                .numeroCuenta(11101001L)
                .nombre("Caja")
                .negocioId(54)
                .build();
        when(negocioActualPort.findNegocioId()).thenReturn(Optional.of(54));
        when(cuentaRepository.buscarImputables(54, "caja")).thenReturn(List.of(cuenta));

        assertThat(useCase.buscar("  caja  ")).containsExactly(cuenta);
        verify(cuentaRepository).buscarImputables(54, "caja");
    }

    @Test
    void textoNuloSeTraduceAVacio() {
        when(negocioActualPort.findNegocioId()).thenReturn(Optional.of(54));
        when(cuentaRepository.buscarImputables(54, "")).thenReturn(List.of());

        assertThat(useCase.buscar(null)).isEmpty();
        verify(cuentaRepository).buscarImputables(54, "");
    }

    @Test
    void fallaSinNegocioActivo() {
        when(negocioActualPort.findNegocioId()).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.buscar("caja"))
                .isInstanceOf(CuentaInvalidaException.class)
                .extracting("codigo")
                .isEqualTo("CUENTA_SIN_NEGOCIO");
    }

}
