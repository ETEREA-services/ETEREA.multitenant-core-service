package eterea.tenant.service.modules.ventas.legacy.hexagonal.invoicedata.infrastructure.mapper;

import eterea.tenant.service.modules.stock.articulo.infrastructure.web.mapper.ArticuloDtoMapper;
import eterea.tenant.service.modules.stock.articulomovimiento.domain.model.ArticuloMovimiento;
import eterea.tenant.service.modules.ventas.legacy.hexagonal.invoicedata.infrastructure.dto.ArticuloMovimientoResponse;
import eterea.tenant.service.modules.ventas.legacy.ConceptoFacturadoException;
import eterea.tenant.service.modules.ventas.legacy.ConceptoFacturado;
import eterea.tenant.service.modules.ventas.legacy.service.ConceptoFacturadoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("invoicedataArticuloMovimientoMapper")
@RequiredArgsConstructor
@Slf4j
public class ArticuloMovimientoMapper {

    private final ConceptoFacturadoService conceptoFacturadoService;
    private final ConceptoFacturadoMapper conceptoFacturadoMapper;
    private final ArticuloDtoMapper articuloDtoMapper;

    public ArticuloMovimientoResponse toResponse(ArticuloMovimiento articuloMovimiento) {
        if (articuloMovimiento == null) {
            return null;
        }
        ConceptoFacturado conceptoFacturado = null;
        try {
            conceptoFacturado = conceptoFacturadoService.findByArticuloMovimientoId(articuloMovimiento.getArticuloMovimientoId());
        } catch (ConceptoFacturadoException e) {
            log.error(e.getMessage());
        }
        return ArticuloMovimientoResponse.builder()
                .cantidad(articuloMovimiento.getCantidad())
                .precioUnitarioSinIva(articuloMovimiento.getPrecioUnitarioSinIva())
                .precioUnitarioConIva(articuloMovimiento.getPrecioUnitarioConIva())
                .precioTotalSinIva(articuloMovimiento.getTotalSinIva())
                .precioTotalConIva(articuloMovimiento.getTotalConIva())
                .articulo(articuloDtoMapper.toResponseForInvoiceData(articuloMovimiento.getArticulo()))
                .conceptoFacturado(conceptoFacturadoMapper.toResponse(conceptoFacturado))
                .build();
    }

}
