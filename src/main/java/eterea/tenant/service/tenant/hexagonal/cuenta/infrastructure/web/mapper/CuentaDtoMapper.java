package eterea.tenant.service.tenant.hexagonal.cuenta.infrastructure.web.mapper;

import eterea.tenant.service.tenant.hexagonal.cuenta.domain.model.Cuenta;
import eterea.tenant.service.tenant.hexagonal.cuenta.infrastructure.web.dto.CuentaResponse;
import eterea.tenant.service.tenant.model.dto.CuentaDto;
import org.springframework.stereotype.Component;

@Component
public class CuentaDtoMapper {

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
}
