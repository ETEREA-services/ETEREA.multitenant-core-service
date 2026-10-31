package eterea.tenant.service.modules.contable.cuenta.infrastructure.web.mapper;

import eterea.tenant.service.modules.contable.cuenta.domain.model.Cuenta;
import eterea.tenant.service.modules.contable.cuenta.domain.model.CuentaNodo;
import eterea.tenant.service.modules.contable.cuenta.infrastructure.web.dto.CuentaNodoResponse;
import eterea.tenant.service.modules.contable.cuenta.infrastructure.web.dto.CuentaRequest;
import eterea.tenant.service.modules.contable.cuenta.infrastructure.web.dto.CuentaDetalleResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class CuentaDtoMapperTest {

    private final CuentaDtoMapper mapper = new CuentaDtoMapper();

    @Test
    void mapeaElRequestAlDominioConFlags() {
        CuentaRequest request = CuentaRequest.builder()
                .numeroCuenta(11_101_001L)
                .nombre("CAJA")
                .cuentaMaestro(new BigDecimal("123"))
                .transfiere(true)
                .movimientoStock(true)
                .ventas(true)
                .compras(true)
                .gastos(true)
                .build();

        Cuenta cuenta = mapper.toDomain(request);

        assertThat(cuenta.getNumeroCuenta()).isEqualTo(11_101_001L);
        assertThat(cuenta.getNombre()).isEqualTo("CAJA");
        assertThat(cuenta.getCuentaMaestro()).isEqualByComparingTo("123");
        assertThat(cuenta.getTransfiere()).isEqualTo((byte) 1);
        assertThat(cuenta.getMovimientoStock()).isEqualTo((byte) 1);
        assertThat(cuenta.getVentas()).isEqualTo((byte) 1);
        assertThat(cuenta.getCompras()).isEqualTo((byte) 1);
        assertThat(cuenta.getGastos()).isEqualTo((byte) 1);
    }

    @Test
    void mapeaLaRespuestaConNivelPadreYBooleanos() {
        Cuenta cuenta = Cuenta.builder()
                .numeroCuenta(11_101_001L)
                .nombre("CAJA")
                .negocioId(1)
                .integra((byte) 1)
                .transfiere((byte) 1)
                .movimientoStock((byte) 0)
                .ventas((byte) 1)
                .compras((byte) 0)
                .gastos((byte) 0)
                .cuentaMaestro(BigDecimal.ZERO)
                .build();

        CuentaDetalleResponse response = mapper.toDetalleResponse(cuenta);

        assertThat(response.getNumeroCuenta()).isEqualTo(11_101_001L);
        assertThat(response.getNegocioId()).isEqualTo(1);
        assertThat(response.isIntegra()).isTrue();
        assertThat(response.isTransfiere()).isTrue();
        assertThat(response.isMovimientoStock()).isFalse();
        assertThat(response.isVentas()).isTrue();
        assertThat(response.isCompras()).isFalse();
        assertThat(response.getGrado()).isEqualTo(5);
        assertThat(response.getNivel()).isEqualTo(5);
        assertThat(response.getGrado4()).isEqualTo(11_101_000L);
        assertThat(response.getPadre()).isEqualTo(11_101_000L);
    }

    @Test
    void mapeaElArbolConLaMarcaDeHuerfana() {
        CuentaNodo raiz = new CuentaNodo(cuenta(10_000_000L), false);
        raiz.agregarHijo(new CuentaNodo(cuenta(11_000_000L), false));
        raiz.agregarHijo(new CuentaNodo(cuenta(99_999_999L), true));

        CuentaNodoResponse response = mapper.toNodoResponse(raiz);

        assertThat(response.getCuenta().getNumeroCuenta()).isEqualTo(10_000_000L);
        assertThat(response.isHuerfana()).isFalse();
        assertThat(response.getHijos()).hasSize(2);
        assertThat(response.getHijos().get(1).isHuerfana()).isTrue();
        assertThat(response.getHijos().get(1).getCuenta().getNumeroCuenta())
                .isEqualTo(99_999_999L);
    }

    private static Cuenta cuenta(long numeroCuenta) {
        return Cuenta.builder()
                .numeroCuenta(numeroCuenta)
                .nombre("CUENTA " + numeroCuenta)
                .build();
    }
}
