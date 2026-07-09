package eterea.tenant.service.tenant.hexagonal.invoicedata.infrastructure.mapper;

import eterea.tenant.service.tenant.hexagonal.empresa.infrastructure.persistence.mapper.EmpresaMapper;
import eterea.tenant.service.tenant.hexagonal.invoicedata.infrastructure.dto.ClienteMovimientoResponse;
import eterea.tenant.service.tenant.model.ClienteMovimiento;
import eterea.tenant.service.tenant.hexagonal.articulomovimiento.application.service.ArticuloMovimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClienteMovimientoMapper {

    private final EmpresaMapper empresaMapper;
    private final ClienteMapper clienteMapper;
    private final MonedaMapper monedaMapper;
    private final ComprobanteResponseMapper comprobanteMapper;
    private final ArticuloMovimientoService articuloMovimientoService;
    private final ArticuloMovimientoMapper articuloMovimientoMapper;

    public ClienteMovimientoResponse toResponse(ClienteMovimiento clienteMovimiento) {
        if (clienteMovimiento == null) {
            return null;
        }
        return ClienteMovimientoResponse.builder()
                .letraComprobante(clienteMovimiento.getLetraComprobante())
                .puntoVenta(clienteMovimiento.getPuntoVenta())
                .numeroComprobante(clienteMovimiento.getNumeroComprobante())
                .observaciones(clienteMovimiento.getObservaciones())
                .letras(clienteMovimiento.getLetras())
                .reservaId(clienteMovimiento.getReservaId())
                .neto(clienteMovimiento.getNeto())
                .montoExento(clienteMovimiento.getMontoExento())
                .montoIva(clienteMovimiento.getMontoIva())
                .montoIvaRni(clienteMovimiento.getMontoIvaRni())
                .importe(clienteMovimiento.getImporte())
                .empresa(empresaMapper.toResponse(clienteMovimiento.getEmpresa()))
                .cliente(clienteMapper.toResponse(clienteMovimiento.getCliente()))
                .moneda(monedaMapper.toResponse(clienteMovimiento.getMoneda()))
                .comprobante(comprobanteMapper.toResponse(clienteMovimiento.getComprobante()))
                .articulos(
                        articuloMovimientoService.findAllByClienteMovimientoId(clienteMovimiento.getClienteMovimientoId())
                                .stream()
                                .map(articuloMovimientoMapper::toResponse)
                                .toList()
                )
                .build();
    }

}
