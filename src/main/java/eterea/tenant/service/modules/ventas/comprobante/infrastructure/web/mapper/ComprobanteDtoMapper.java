package eterea.tenant.service.modules.ventas.comprobante.infrastructure.web.mapper;

import eterea.tenant.service.modules.ventas.comprobante.domain.model.Comprobante;
import eterea.tenant.service.modules.ventas.comprobante.infrastructure.web.dto.ComprobanteResponse;
import eterea.tenant.service.modules.contable.api.CuentaQuery;
import eterea.tenant.service.modules.contable.api.CuentaResumen;
import eterea.tenant.service.modules.ventas.legacy.ComprobanteDto;
import eterea.tenant.service.modules.contable.legacy.CuentaDto;
import org.springframework.stereotype.Component;

@Component
public class ComprobanteDtoMapper {

    private final CuentaQuery cuentaQuery;

    public ComprobanteDtoMapper(CuentaQuery cuentaQuery) {
        this.cuentaQuery = cuentaQuery;
    }

    public ComprobanteResponse toResponse(Comprobante domain) {
        if (domain == null) {
            return null;
        }
        return ComprobanteResponse.builder()
                .comprobanteId(domain.getComprobanteId())
                .descripcion(domain.getDescripcion())
                .negocioId(domain.getNegocioId())
                .modulo(domain.getModulo())
                .stock(domain.getStock())
                .rendicion(domain.getRendicion())
                .ordenPago(domain.getOrdenPago())
                .aplicaPendiente(domain.getAplicaPendiente())
                .cuentaCorriente(domain.getCuentaCorriente())
                .debita(domain.getDebita())
                .iva(domain.getIva())
                .ganancias(domain.getGanancias())
                .aplicable(domain.getAplicable())
                .libroIva(domain.getLibroIva())
                .letraComprobante(domain.getLetraComprobante())
                .recibo(domain.getRecibo())
                .contado(domain.getContado())
                .transferencia(domain.getTransferencia())
                .imprime(domain.getImprime())
                .comprobanteIdAnulacion(domain.getComprobanteIdAnulacion())
                .centroStockIdEntrega(domain.getCentroStockIdEntrega())
                .centroStockIdRecibe(domain.getCentroStockIdRecibe())
                .diasVigencia(domain.getDiasVigencia())
                .concepto(domain.getConcepto())
                .personal(domain.getPersonal())
                .comanda(domain.getComanda())
                .puntoVenta(domain.getPuntoVenta())
                .codigoMozo(domain.getCodigoMozo())
                .transferir(domain.getTransferir())
                .numeroCuenta(domain.getNumeroCuenta())
                .nivel(domain.getNivel())
                .fiscalizadora(domain.getFiscalizadora())
                .invisible(domain.getInvisible())
                .comprobanteAfipId(domain.getComprobanteAfipId())
                .facturaElectronica(domain.getFacturaElectronica())
                .asociado(domain.getAsociado())
                .build();
    }

    public ComprobanteDto toComprobanteDto(Comprobante domain) {
        if (domain == null) {
            return null;
        }
        return ComprobanteDto.builder()
                .comprobanteId(domain.getComprobanteId())
                .descripcion(domain.getDescripcion())
                .negocioId(domain.getNegocioId())
                .modulo(domain.getModulo())
                .stock(domain.getStock())
                .rendicion(domain.getRendicion())
                .ordenPago(domain.getOrdenPago())
                .aplicaPendiente(domain.getAplicaPendiente())
                .cuentaCorriente(domain.getCuentaCorriente())
                .debita(domain.getDebita())
                .iva(domain.getIva())
                .ganancias(domain.getGanancias())
                .aplicable(domain.getAplicable())
                .libroIva(domain.getLibroIva())
                .letraComprobante(domain.getLetraComprobante())
                .recibo(domain.getRecibo())
                .contado(domain.getContado())
                .transferencia(domain.getTransferencia())
                .imprime(domain.getImprime())
                .comprobanteIdAnulacion(domain.getComprobanteIdAnulacion())
                .centroStockIdEntrega(domain.getCentroStockIdEntrega())
                .centroStockIdRecibe(domain.getCentroStockIdRecibe())
                .diasVigencia(domain.getDiasVigencia())
                .concepto(domain.getConcepto())
                .personal(domain.getPersonal())
                .comanda(domain.getComanda())
                .puntoVenta(domain.getPuntoVenta())
                .codigoMozo(domain.getCodigoMozo())
                .transferir(domain.getTransferir())
                .numeroCuenta(domain.getNumeroCuenta())
                .nivel(domain.getNivel())
                .fiscalizadora(domain.getFiscalizadora())
                .invisible(domain.getInvisible())
                .comprobanteAfipId(domain.getComprobanteAfipId())
                .facturaElectronica(domain.getFacturaElectronica())
                .asociado(domain.getAsociado())
                .cuenta(toCuentaDto(cuentaQuery.findByNumeroCuenta(domain.getNumeroCuenta()).orElse(null)))
                .build();
    }

    private CuentaDto toCuentaDto(CuentaResumen cuenta) {
        if (cuenta == null) {
            return null;
        }
        return CuentaDto.builder()
                .numeroCuenta(cuenta.numeroCuenta())
                .nombre(cuenta.nombre())
                .negocioId(cuenta.negocioId())
                .integra(cuenta.integra())
                .transfiere(cuenta.transfiere())
                .movimientoStock(cuenta.movimientoStock())
                .cuentaMaestro(cuenta.cuentaMaestro())
                .grado(cuenta.grado())
                .grado1(cuenta.grado1())
                .grado2(cuenta.grado2())
                .grado3(cuenta.grado3())
                .grado4(cuenta.grado4())
                .ventas(cuenta.ventas())
                .compras(cuenta.compras())
                .gastos(cuenta.gastos())
                .build();
    }
}
