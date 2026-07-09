package eterea.tenant.service.tenant.model.dto;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import eterea.tenant.service.tenant.kotlin.model.ValorMovimiento;

@Component
public class ValorMovimientoDtoMapper implements Function<ValorMovimiento, ValorMovimientoDto> {

   @Override
   public ValorMovimientoDto apply(ValorMovimiento valorMovimiento) {
      return new ValorMovimientoDto(
            valorMovimiento.getValorMovimientoId(),
            valorMovimiento.getCierreCajaId(),
            valorMovimiento.getNegocioId(),
            valorMovimiento.getValor() != null ? valorMovimiento.getValor().getConcepto() : null,
            valorMovimiento.getFechaContable(),
            valorMovimiento.getImporte(),
            valorMovimiento.getClienteMovimientoId(),
            valorMovimiento.getClienteId(),
            valorMovimiento.getNumeroCuenta(),
            valorMovimiento.getCreated(),
            valorMovimiento.getUpdated());
   }
}