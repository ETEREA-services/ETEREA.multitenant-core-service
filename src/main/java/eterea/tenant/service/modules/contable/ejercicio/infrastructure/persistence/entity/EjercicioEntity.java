package eterea.tenant.service.modules.contable.ejercicio.infrastructure.persistence.entity;

import eterea.tenant.service.tool.Auditable;
import eterea.tenant.service.tool.Jsonifyable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Mapeo de la tabla legacy {@code ejercicios}.
 *
 * <p>No extiende {@code Auditable} porque la tabla tiene {@code created} pero
 * no se verificó {@code updated}. El alta setea {@code created} en el
 * servidor y la columna no se actualiza después.</p>
 */
@Getter
@Setter
@Entity
@Table(name = "ejercicios")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EjercicioEntity implements Jsonifyable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eje_id")
    private Long ejercicioId;

    @Column(name = "eje_nombre", nullable = false, length = 128)
    private String nombre;

    @Column(name = "eje_fechainicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "eje_fechafin", nullable = false)
    private LocalDate fechaFin;

    @Column(name = "eje_bloqueado", nullable = false)
    private boolean bloqueado;

    @Column(name = "created", updatable = false)
    private LocalDateTime created;

    @PrePersist
    void prePersist() {
        if (created == null) {
            created = LocalDateTime.now();
        }
    }

}
