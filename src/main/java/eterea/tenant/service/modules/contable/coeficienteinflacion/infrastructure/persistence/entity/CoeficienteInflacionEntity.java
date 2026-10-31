package eterea.tenant.service.modules.contable.coeficienteinflacion.infrastructure.persistence.entity;

import eterea.tenant.service.tool.Jsonifyable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Mapeo de la tabla legacy {@code coeficiente_inflacion}.
 *
 * <p>No extiende {@code Auditable} porque la tabla tiene {@code created} pero
 * no se verificó {@code updated}. El alta setea {@code created} en el servidor
 * y la columna no se actualiza después.</p>
 */
@Getter
@Setter
@Entity
@Table(name = "coeficiente_inflacion",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_coeficiente_inflacion_anho_mes",
                columnNames = {"anho", "mes"}))
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoeficienteInflacionEntity implements Jsonifyable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coeficienteinflacion_id")
    private Long coeficienteInflacionId;

    @Column(name = "anho", nullable = false)
    private Integer anho;

    @Column(name = "mes", nullable = false)
    private Integer mes;

    @Column(name = "coeficiente", precision = 18, scale = 6)
    private BigDecimal coeficiente;

    @Column(name = "created", updatable = false)
    private LocalDateTime created;

    @PrePersist
    void prePersist() {
        if (created == null) {
            created = LocalDateTime.now();
        }
    }

}
