package eterea.tenant.service.modules.contable.ejercicio.domain.model;

import eterea.tenant.service.tool.Jsonifyable;
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
public class Ejercicio implements Jsonifyable {

    private Long ejercicioId;
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private boolean bloqueado;
    private LocalDateTime created;

}
