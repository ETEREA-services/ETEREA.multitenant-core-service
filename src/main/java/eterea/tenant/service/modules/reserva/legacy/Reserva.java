package eterea.tenant.service.modules.reserva.legacy;

import eterea.tenant.service.modules.platform.legacy.Cliente;
import com.fasterxml.jackson.annotation.JsonFormat;
import eterea.tenant.service.tool.Auditable;
import eterea.tenant.service.tool.Jsonifier;
import eterea.tenant.service.tool.Jsonifyable;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class Reserva extends Auditable implements Jsonifyable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "res_id")
    private Long reservaId;

    @Column(name = "res_neg_id")
    private Integer negocioId;

    @Column(name = "res_cli_id")
    private Long clienteId;

    @Column(name = "res_fecha")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaToma;

    @Column(name = "res_in")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaInServicio;

    @Column(name = "res_out")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaOutServicio;

    @Column(name = "res_vto")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaVencimiento;

    @Column(name = "res_horavto")
    private LocalTime horaVencimiento;

    @Column(name = "res_avisomail")
    @Builder.Default
    private byte avisoMail = 0;

    @Column(name = "res_pendiente")
    @Builder.Default
    private byte pendiente = 0;

    @Column(name = "res_confirmada")
    @Builder.Default
    private byte confirmada = 0;

    @Column(name = "res_facturada")
    @Builder.Default
    private byte facturada = 0;

    @Column(name = "res_anulada")
    @Builder.Default
    private byte anulada = 0;

    @Column(name = "res_eliminada")
    @Builder.Default
    private byte eliminada = 0;

    @Builder.Default
    private byte verificada = 0;

    @Column(name = "res_nombrepax")
    @Builder.Default
    private String nombrePax = "";

    @Column(name = "res_paxs")
    @Builder.Default
    private int cantidadPaxs = 0;

    @Column(name = "res_observaciones")
    @Builder.Default
    private String observaciones = "";

    @Column(name = "res_vou_id")
    private Long voucherId;

    @Column(name = "res_pagacomision")
    @Builder.Default
    private byte pagaComision = 0;

    @Column(name = "res_obscomision")
    @Builder.Default
    private String observacionesComision = "";

    @Column(name = "res_comisionpagada")
    @Builder.Default
    private byte comisionPagada = 0;

    @Column(name = "res_pagacacheuta")
    @Builder.Default
    private byte pagaCacheuta = 0;

    @Column(name = "res_facturadofuera")
    @Builder.Default
    private byte facturadoFuera = 0;

    @Column(name = "res_reservaarticulo")
    @Builder.Default
    private String reservaArticulos = "";

    @Column(name = "res_usuario")
    @Builder.Default
    private String usuario = "";

    @Column(name = "res_cliente")
    @Builder.Default
    private String contacto = "";

    @Column(name = "res_reo_ID")
    private Integer reservaOrigenId;

    @Column(name = "facturarextranjero")
    @Builder.Default
    private byte facturarExtranjero = 0;

    @Column(name = "fecha_abierta")
    @Builder.Default
    private byte fechaAbierta = 0;

    private String trackUuid;

    @OneToOne
    @JoinColumn(name = "res_cli_id", insertable = false, updatable = false)
    private Cliente cliente;

}
