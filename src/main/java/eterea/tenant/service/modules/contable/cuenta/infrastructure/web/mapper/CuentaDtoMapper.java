package eterea.tenant.service.modules.contable.cuenta.infrastructure.web.mapper;

import eterea.tenant.service.modules.contable.cuenta.domain.model.CodigoCuenta;
import eterea.tenant.service.modules.contable.cuenta.domain.model.Cuenta;
import eterea.tenant.service.modules.contable.cuenta.domain.model.CuentaNodo;
import eterea.tenant.service.modules.contable.cuenta.infrastructure.web.dto.CuentaDetalleResponse;
import eterea.tenant.service.modules.contable.cuenta.infrastructure.web.dto.CuentaNodoResponse;
import eterea.tenant.service.modules.contable.cuenta.infrastructure.web.dto.CuentaRequest;
import eterea.tenant.service.modules.contable.cuenta.infrastructure.web.dto.CuentaResponse;
import eterea.tenant.service.modules.contable.cuenta.infrastructure.web.dto.CuentaUpdateRequest;
import eterea.tenant.service.modules.contable.legacy.CuentaDto;
import org.springframework.stereotype.Component;

@Component
public class CuentaDtoMapper {

    public Cuenta toDomain(CuentaRequest request) {
        return Cuenta.builder()
                .numeroCuenta(request.getNumeroCuenta())
                .nombre(request.getNombre())
                .cuentaMaestro(request.getCuentaMaestro())
                .transfiere(flag(request.isTransfiere()))
                .movimientoStock(flag(request.isMovimientoStock()))
                .ventas(flag(request.isVentas()))
                .compras(flag(request.isCompras()))
                .gastos(flag(request.isGastos()))
                .build();
    }

    public Cuenta toDomain(CuentaUpdateRequest request) {
        return Cuenta.builder()
                .nombre(request.getNombre())
                .cuentaMaestro(request.getCuentaMaestro())
                .transfiere(flag(request.isTransfiere()))
                .movimientoStock(flag(request.isMovimientoStock()))
                .ventas(flag(request.isVentas()))
                .compras(flag(request.isCompras()))
                .gastos(flag(request.isGastos()))
                .build();
    }

    public CuentaResponse toResponse(Cuenta domain) {
        if (domain == null) {
            return null;
        }
        return CuentaResponse.builder()
                .numeroCuenta(domain.getNumeroCuenta())
                .nombre(domain.getNombre())
                .negocioId(domain.getNegocioId())
                .integra(domain.getIntegra())
                .transfiere(domain.getTransfiere())
                .movimientoStock(domain.getMovimientoStock())
                .cuentaMaestro(domain.getCuentaMaestro())
                .grado(domain.getGrado())
                .grado1(domain.getGrado1())
                .grado2(domain.getGrado2())
                .grado3(domain.getGrado3())
                .grado4(domain.getGrado4())
                .ventas(domain.getVentas())
                .compras(domain.getCompras())
                .gastos(domain.getGastos())
                .build();
    }

    public CuentaDetalleResponse toDetalleResponse(Cuenta cuenta) {
        CodigoCuenta codigo = CodigoCuenta.of(cuenta.getNumeroCuenta());
        return CuentaDetalleResponse.builder()
                .numeroCuenta(cuenta.getNumeroCuenta())
                .nombre(cuenta.getNombre())
                .negocioId(cuenta.getNegocioId())
                .integra(cuenta.getIntegra() != null && cuenta.getIntegra() == 1)
                .transfiere(cuenta.getTransfiere() != null && cuenta.getTransfiere() == 1)
                .movimientoStock(cuenta.getMovimientoStock() != null
                        && cuenta.getMovimientoStock() == 1)
                .cuentaMaestro(cuenta.getCuentaMaestro())
                .grado(codigo.nivel())
                .grado1(codigo.grado1())
                .grado2(codigo.grado2())
                .grado3(codigo.grado3())
                .grado4(codigo.grado4())
                .padre(codigo.padre())
                .nivel(codigo.nivel())
                .ventas(cuenta.getVentas() != null && cuenta.getVentas() == 1)
                .compras(cuenta.getCompras() != null && cuenta.getCompras() == 1)
                .gastos(cuenta.getGastos() != null && cuenta.getGastos() == 1)
                .build();
    }

    public CuentaNodoResponse toNodoResponse(CuentaNodo nodo) {
        return CuentaNodoResponse.builder()
                .cuenta(toDetalleResponse(nodo.getCuenta()))
                .huerfana(nodo.isHuerfana())
                .hijos(nodo.getHijos().stream().map(this::toNodoResponse).toList())
                .build();
    }

    public CuentaDto toCuentaDto(Cuenta domain) {
        if (domain == null) {
            return null;
        }
        return CuentaDto.builder()
                .numeroCuenta(domain.getNumeroCuenta())
                .nombre(domain.getNombre())
                .negocioId(domain.getNegocioId())
                .integra(domain.getIntegra())
                .transfiere(domain.getTransfiere())
                .movimientoStock(domain.getMovimientoStock())
                .cuentaMaestro(domain.getCuentaMaestro())
                .grado(domain.getGrado())
                .grado1(domain.getGrado1())
                .grado2(domain.getGrado2())
                .grado3(domain.getGrado3())
                .grado4(domain.getGrado4())
                .ventas(domain.getVentas())
                .compras(domain.getCompras())
                .gastos(domain.getGastos())
                .build();
    }

    private static byte flag(boolean valor) {
        return valor ? (byte) 1 : (byte) 0;
    }
}
