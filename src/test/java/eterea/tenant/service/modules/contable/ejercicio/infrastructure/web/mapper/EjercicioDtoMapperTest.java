package eterea.tenant.service.modules.contable.ejercicio.infrastructure.web.mapper;

import eterea.tenant.service.modules.contable.ejercicio.domain.model.Ejercicio;
import eterea.tenant.service.modules.contable.ejercicio.domain.model.EjercicioSugerido;
import eterea.tenant.service.modules.contable.ejercicio.domain.model.EstadoEjercicio;
import eterea.tenant.service.modules.contable.ejercicio.infrastructure.web.dto.EjercicioRequest;
import eterea.tenant.service.modules.contable.ejercicio.infrastructure.web.dto.EjercicioResponse;
import eterea.tenant.service.modules.contable.ejercicio.infrastructure.web.dto.SiguienteEjercicioResponse;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class EjercicioDtoMapperTest {

    private final EjercicioDtoMapper mapper = new EjercicioDtoMapper();

    @Test
    void mapeaElRequestAlDominio() {
        EjercicioRequest request = EjercicioRequest.builder()
                .nombre("Ejercicio 2027")
                .fechaInicio(LocalDate.of(2027, 1, 1))
                .fechaFin(LocalDate.of(2027, 12, 31))
                .build();

        Ejercicio ejercicio = mapper.toDomain(request);

        assertThat(ejercicio.getNombre()).isEqualTo("Ejercicio 2027");
        assertThat(ejercicio.getFechaInicio()).isEqualTo(LocalDate.of(2027, 1, 1));
        assertThat(ejercicio.getFechaFin()).isEqualTo(LocalDate.of(2027, 12, 31));
    }

    @Test
    void calculaElEstadoEnLaRespuesta() {
        LocalDate hoy = LocalDate.now();
        Ejercicio ejercicio = Ejercicio.builder()
                .ejercicioId(1L)
                .nombre("Ejercicio en curso")
                .fechaInicio(hoy.withDayOfYear(1))
                .fechaFin(hoy.withDayOfYear(hoy.lengthOfYear()))
                .bloqueado(true)
                .build();

        EjercicioResponse response = mapper.toResponse(ejercicio);

        assertThat(response.getEstado()).isEqualTo(EstadoEjercicio.EN_CURSO);
        assertThat(response.isBloqueado()).isTrue();
        assertThat(response.getEjercicioId()).isEqualTo(1L);
    }

    @Test
    void mapeaLaSugerencia() {
        EjercicioSugerido sugerido = new EjercicioSugerido(
                true,
                "Ejercicio 2027",
                LocalDate.of(2027, 1, 1),
                LocalDate.of(2027, 12, 31),
                "ULTIMO_EJERCICIO",
                null);

        SiguienteEjercicioResponse response = mapper.toResponse(sugerido);

        assertThat(response.isDisponible()).isTrue();
        assertThat(response.getNombre()).isEqualTo("Ejercicio 2027");
        assertThat(response.getOrigen()).isEqualTo("ULTIMO_EJERCICIO");
    }

}
