package eterea.tenant.service.tenant.hexagonal.proveedormovimiento.infrastructure.web.mapper;

import eterea.tenant.service.tenant.hexagonal.proveedormovimiento.domain.model.ResumenIvaComprasMensualPosicion;
import eterea.tenant.service.tenant.hexagonal.proveedormovimiento.infrastructure.web.dto.ResumenIvaComprasMensualPosicionResponse;
import eterea.tenant.service.tenant.service.PosicionIvaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResumenIvaComprasMensualPosicionDtoMapper {

    private final PosicionIvaService posicionIvaService;

    public ResumenIvaComprasMensualPosicionResponse toResponse(ResumenIvaComprasMensualPosicion resumen) {
        if (resumen == null) {
            return null;
        }
        return ResumenIvaComprasMensualPosicionResponse.builder()
                .negocioId(resumen.getNegocioId())
                .anho(resumen.getAnho())
                .mes(resumen.getMes())
                .posicion(resumen.getPosicion())
                .posicionNombre(posicionIvaService.findByPosicionId(resumen.getPosicion()).getNombre())
                .neto(resumen.getNeto())
                .facturadoC(resumen.getFacturadoC())
                .gastosNoGravados(resumen.getGastosNoGravados())
                .iva21(resumen.getIva21())
                .iva27(resumen.getIva27())
                .iva105(resumen.getIva105())
                .percepcionIva(resumen.getPercepcionIva())
                .percepcionIngresosBrutos(resumen.getPercepcionIngresosBrutos())
                .total(resumen.getTotal())
                .build();
    }

}
