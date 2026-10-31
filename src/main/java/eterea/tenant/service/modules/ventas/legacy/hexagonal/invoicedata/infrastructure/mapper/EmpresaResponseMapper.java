package eterea.tenant.service.modules.ventas.legacy.hexagonal.invoicedata.infrastructure.mapper;

import eterea.tenant.service.modules.platform.legacy.hexagonal.empresa.infrastructure.persistence.entity.EmpresaEntity;
import eterea.tenant.service.modules.ventas.legacy.hexagonal.invoicedata.infrastructure.dto.EmpresaResponse;
import org.springframework.stereotype.Component;

@Component
public class EmpresaResponseMapper {

    public EmpresaResponse toResponse(EmpresaEntity empresaEntity) {
        if (empresaEntity == null) {
            return null;
        }
        return EmpresaResponse.builder()
                .nombreFantasia(empresaEntity.getNombreFantasia())
                .razonSocial(empresaEntity.getRazonSocial())
                .domicilio(empresaEntity.getDomicilio())
                .telefono(empresaEntity.getTelefono())
                .condicionIva(empresaEntity.getCondicionIva())
                .cuit(empresaEntity.getCuit())
                .ingresosBrutos(empresaEntity.getIngresosBrutos())
                .inicioActividades(empresaEntity.getInicioActividades())
                .build();
    }
}
