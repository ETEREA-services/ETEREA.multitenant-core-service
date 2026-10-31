package eterea.tenant.service.modules.tesoreria.legacy;

import eterea.tenant.service.tool.Auditable;
import eterea.tenant.service.tool.Jsonifier;
import eterea.tenant.service.tool.Jsonifyable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cgosvalores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class Valor extends Auditable implements Jsonifyable {

    @Id
    @Column(name = "codigo")
    private Integer valorId;

    private String concepto;

    @Column(name = "cva_neg_id")
    private Integer negocioId;

    @Column(name = "modulocompras")
    @Builder.Default
    private byte compras = 0;

    @Column(name = "moduloventas")
    @Builder.Default
    private byte ventas = 0;

    @Builder.Default
    private byte numerable = 0;

    @Builder.Default
    private byte duplicados = 0;

    @Column(name = "fechaemi")
    @Builder.Default
    private byte fechaEmision = 0;

    @Column(name = "fechavto")
    @Builder.Default
    private byte fechaVencimiento = 0;

    @Column(name = "cgocontable")
    private Long numeroCuenta;

    @Builder.Default
    private byte titular = 0;

    @Builder.Default
    private byte banco = 0;

    @Column(name = "chtercero")
    @Builder.Default
    private byte chequeTercero = 0;

    @Column(name = "ctacte")
    @Builder.Default
    private byte cuentaCorriente = 0;

    @Column(name = "retib")
    @Builder.Default
    private byte retencionIngresosBrutos = 0;

    @Column(name = "cva_retgcias")
    @Builder.Default
    private byte retencionGanancias = 0;

    @Column(name = "cva_retiva")
    @Builder.Default
    private byte retencionIva = 0;

    @Column(name = "cva_autonumerable")
    @Builder.Default
    private byte autoNumerable = 0;

    @Column(name = "cva_mon_id")
    private Integer monedaId;

    @Column(name = "cva_invisible")
    @Builder.Default
    private byte invisible = 0;

    @Builder.Default
    private byte restaurant = 0;

    @Builder.Default
    private byte tarjeta = 0;

}
