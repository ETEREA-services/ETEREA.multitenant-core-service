package eterea.tenant.service.modules.compras.legacy;

import eterea.tenant.service.tool.Auditable;
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
@Table(name = "proveedores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class Proveedor extends Auditable {

    @Id
    @Column(name = "codigo")
    private Long proveedorId;

    @Column(name = "razon")
    @Builder.Default
    private String razonSocial = "";

    @Column(name = "Prv_Neg_ID")
    private Integer negocioId;

    @Builder.Default
    private String cuit = "";

    @Builder.Default
    private String domicilio = "";

    @Column(name = "tel")
    @Builder.Default
    private String telefono = "";

    @Builder.Default
    private String fax = "";

    @Builder.Default
    private String email = "";

    @Builder.Default
    private int posicion = 0;

    @Builder.Default
    private String celular = "";

    @Column(name = "nroinscib")
    @Builder.Default
    private String numeroInscripcionIngresosBrutos = "";

    @Column(name = "ibcontribuyente_id")
    private Integer ingresosBrutosContribuyenteId;

    @Column(name = "prv_prc_id")
    private Integer proveedorCategoriaId;

    @Column(name = "ibcategoria_id")
    private Integer ingresosBrutosCategoriaId;

    @Column(name = "prv_rep_codigo")
    private Integer reparticionId;

    @Builder.Default
    private byte transporte = 0;

}
