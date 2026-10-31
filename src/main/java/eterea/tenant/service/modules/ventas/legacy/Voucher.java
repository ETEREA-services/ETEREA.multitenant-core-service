package eterea.tenant.service.modules.ventas.legacy;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import eterea.tenant.service.tool.Jsonifyable;
import jakarta.persistence.*;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class Voucher implements Jsonifyable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vou_id")
    private Long voucherId;

    @Column(name = "vou_fechareserva")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaToma;

    @Column(name = "vou_fechain")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaServicio;

    @Column(name = "vou_vto")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaVencimiento;

    @Column(name = "vou_horavto")
    @Builder.Default
    private LocalTime horaVencimiento = LocalTime.of(0, 0, 0);

    @Column(name = "vou_nombrepax")
    private String nombrePax;

    @Column(name = "vou_paxs")
    private Integer paxs;

    @Column(name = "vou_subeen")
    @Builder.Default
    private String subeEn = "";

    @Column(name = "vou_productos")
    private String productos;

    @Column(name = "vou_voucher")
    private Byte tieneVoucher;

    @Column(name = "vou_cli_id")
    private Long clienteId;

    @Column(name = "vou_observ")
    private String observaciones;

    @Column(name = "vou_confirmado")
    private Byte confirmado;

    private Byte pagaCacheuta;

    @Column(name = "vou_hot_id")
    private Integer hotelId;

    @Column(name = "vou_cliente")
    @Builder.Default
    private String contacto = "";

    @Column(name = "vou_paxsreales")
    private Integer paxsReales;

    @Column(name = "vou_pro_id")
    private Integer proveedorId;

    @Column(name = "vou_planilla")
    @Builder.Default
    private String planilla = "";

    @Column(name = "vou_res_id")
    private Long reservaId;

    @Column(name = "vou_nrovoucher")
    private String numeroVoucher;

    @Column(name = "vou_usuario")
    private String usuario;

    @Column(name = "vou_fecharecepcion")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaRecepcion;

    @Column(name = "vou_fechaemision")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaEmision;

    @Column(name = "vou_numero")
    private String numero;

    @Column(name = "vou_cantidadpax")
    private Integer cantidadPax;

    @Column(name = "vou_nombre")
    private String nombre;

    @Column(name = "vou_contraslado")
    private Byte conTraslado;

    @Column(name = "vou_paxsnoshow")
    private Integer paxsNoShow;

    @Column(name = "vou_reo_id")
    private Integer reservaOrigenId;

    @Builder.Default
    private byte fechaAbierta = 0;

    @Builder.Default
    private byte ventaInternet = 0;

    private String trackUuid;






}
