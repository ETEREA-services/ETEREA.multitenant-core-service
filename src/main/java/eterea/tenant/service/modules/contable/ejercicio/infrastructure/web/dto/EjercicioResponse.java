package eterea.tenant.service.modules.contable.ejercicio.infrastructure.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import eterea.tenant.service.modules.contable.ejercicio.domain.model.EstadoEjercicio;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EjercicioResponse {

    private Long ejercicioId;
    private String nombre;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaFin;

    private boolean bloqueado;
    private EstadoEjercicio estado;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime created;

}
