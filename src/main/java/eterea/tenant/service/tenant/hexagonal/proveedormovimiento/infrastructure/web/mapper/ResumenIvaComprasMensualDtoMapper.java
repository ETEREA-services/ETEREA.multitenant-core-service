package eterea.tenant.service.tenant.hexagonal.proveedormovimiento.infrastructure.web.mapper;

import eterea.tenant.service.tenant.hexagonal.negocio.application.service.NegocioService;
import eterea.tenant.service.tenant.hexagonal.negocio.domain.model.Negocio;
import eterea.tenant.service.tenant.hexagonal.proveedormovimiento.domain.model.ResumenIvaComprasMensual;
import eterea.tenant.service.tenant.hexagonal.proveedormovimiento.infrastructure.web.dto.ResumenIvaComprasMensualResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResumenIvaComprasMensualDtoMapper {

    private final NegocioService negocioService;

    public ResumenIvaComprasMensualResponse toResponse(ResumenIvaComprasMensual resumen) {
        if (resumen == null) {
            return null;
        }
        return ResumenIvaComprasMensualResponse.builder()
                .negocioId(resumen.getNegocioId())
                .negocio(negocioService.getNegocioById(resumen.getNegocioId()).getNombre())
                .anho(resumen.getAnho())
                .mes(resumen.getMes())
                .neto(resumen.getNeto())
                .facturadoC(resumen.getFacturadoC())
                .gastosNoGravados(resumen.getGastosNoGravados())
                .iva21(resumen.getIva21())
                .iva27(resumen.getIva27())
                .iva105(resumen.getIva105())
                .percepcionIva(resumen.getPercepcionIva())
                .percepcionIngresosBrutos(resumen.getPercepcionIngresosBrutos())
                .totalCalculado(resumen.getTotalCalculado())
                .total(resumen.getTotal())
                .build();
    }

}
