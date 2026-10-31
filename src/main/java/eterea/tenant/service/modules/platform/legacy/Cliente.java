package eterea.tenant.service.modules.platform.legacy;

import com.fasterxml.jackson.annotation.JsonFormat;
import eterea.tenant.service.tool.Auditable;
import eterea.tenant.service.modules.platform.legacy.PosicionIva;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class Cliente extends Auditable {

    @Id
    @Column(name = "codigo")
    private Long clienteId;

    private String nombre;

    @Column(name = "cli_neg_id")
    private Integer negocioId;

    @Builder.Default
    private String cuit = "";

    @Column(name = "razon")
    @Builder.Default
    private String razonSocial = "";

    @Column(name = "cli_fantasia")
    @Builder.Default
    private String nombreFantasia = "";

    @Column(name = "cli_fecharestaurant")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaRestaurant;

    @Column(name = "cli_cantpaxs")
    @Builder.Default
    private int cantidadPaxs = 0;

    @Column(name = "cli_tipo")
    @Builder.Default
    private int tipoCliente = 0;

    @Builder.Default
    private String domicilio = "";

    @Column(name = "tel")
    @Builder.Default
    private String telefono = "";

    @Builder.Default
    private String fax = "";

    @Builder.Default
    private String email = "";

    @Column(name = "celular")
    @Builder.Default
    private String numeroMovil = "";

    @Column(name = "posicion")
    @Builder.Default
    private int posicionIva = 0;

    @Builder.Default
    private int constante = 0;

    @Builder.Default
    private int documentoId = 0;

    @Column(name = "tipodoc")
    @Builder.Default
    private String tipoDocumento = "";

    @Column(name = "nrodoc")
    @Builder.Default
    private String numeroDocumento = "";

    @Column(name = "limitecredito")
    @Builder.Default
    private BigDecimal limiteCredito = BigDecimal.ZERO;

    @Builder.Default
    private String nacionalidad = "";

    @Column(name = "cli_cca_id")
    private Integer clienteCategoriaId;

    @Column(name = "cli_idimpositivo")
    @Builder.Default
    private String idImpositivo = "";

    @Column(name = "facturarextranjero")
    @Builder.Default
    private byte facturarExtranjero = 0;

    @Builder.Default
    private byte bloqueado = 0;

    @Builder.Default
    private byte discapacitado = 0;

    @OneToOne(optional = true)
    @JoinColumn(name = "posicion", insertable = false, updatable = false)
    private PosicionIva posicion;

}
