package eterea.tenant.service.modules.contable.mayor.reporte.infrastructure.web.mapper;

import eterea.tenant.service.modules.contable.mayor.domain.model.MayorControl;
import eterea.tenant.service.modules.contable.mayor.reporte.infrastructure.web.dto.MayorControlResponse;
import eterea.tenant.service.modules.contable.mayor.reporte.infrastructure.web.dto.MayorModuloResponse;
import org.springframework.stereotype.Component;

/**
 * Mapea los controles del dominio al contrato HTTP.
 */
@Component
public class MayorDtoMapper {

    public MayorControlResponse toControl(MayorControl control) {
        return new MayorControlResponse(
                control.totalMovimientos(),
                control.movimientosExcluidos(),
                control.modulos().stream()
                        .map(modulo -> new MayorModuloResponse(
                                modulo.moduloId(), modulo.nombre(), modulo.cantidad()))
                        .toList(),
                control.comprobantesSinTipo());
    }

}
