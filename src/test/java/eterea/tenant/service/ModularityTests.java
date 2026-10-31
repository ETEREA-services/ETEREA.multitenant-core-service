package eterea.tenant.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

/**
 * Verifica las fronteras y los ciclos de los módulos de negocio.
 */
class ModularityTests {

    private static final ApplicationModules MODULES =
            ApplicationModules.of("eterea.tenant.service.modules");

    @Test
    void verificaFronterasDeModulos() {
        MODULES.verify();
    }

    @Test
    void detectaLosModulosEsperados() {
        assertThat(MODULES.stream().map(module -> module.getIdentifier().toString()))
                .containsExactlyInAnyOrder(
                        "compras", "contable", "personal", "platform", "reporte",
                        "reserva", "stock", "tesoreria", "ventas");
    }

}
