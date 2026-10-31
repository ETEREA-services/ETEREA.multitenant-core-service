package eterea.tenant.service.modules.ventas.legacy.service.facade;

import eterea.tenant.service.modules.platform.legacy.service.ClienteService;
import eterea.tenant.service.modules.ventas.legacy.service.ReservaService;
import eterea.tenant.service.modules.platform.legacy.model.Track;
import eterea.tenant.service.modules.reserva.legacy.ReservaContext;
import eterea.tenant.service.modules.ventas.comprobante.domain.model.Comprobante;
import eterea.tenant.service.modules.ventas.legacy.Voucher;
import eterea.tenant.service.modules.ventas.comprobante.application.service.ComprobanteService;
import eterea.tenant.service.modules.platform.legacy.hexagonal.empresa.application.service.EmpresaService;
import eterea.tenant.service.modules.ventas.legacy.hexagonal.facturacion.arca.nacional.infrastructure.web.dto.FacturacionDto;
import eterea.tenant.service.modules.platform.legacy.service.ParametroService;
import eterea.tenant.service.modules.reserva.legacy.service.ReservaContextService;
import eterea.tenant.service.modules.platform.legacy.service.TrackService;
import eterea.tenant.service.modules.ventas.legacy.service.VoucherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransaccionFacturaProgramaDiaService {

    private final FacturacionService facturacionService;
    private final ReservaService reservaService;
    private final ClienteService clienteService;
    private final VoucherService voucherService;
    private final ComprobanteService comprobanteService;
    private final ParametroService parametroService;
    private final ReservaContextService reservaContextService;
    private final TrackService trackService;
    private final EmpresaService empresaService;

    public void registroTransaccionFacturaProgramaDia(Long orderNumberId, FacturacionDto facturacionDto, Boolean soloFactura, Boolean dryRun) {
        log.debug("Processing TransaccionFacturaProgramaDiaService.registroTransaccionFacturaProgramaDia");
        log.debug("FacturacionDto -> {}", facturacionDto.jsonify());
        var voucher = voucherService.findByNumeroVoucher(String.valueOf(orderNumberId));
        log.debug("Voucher -> {}", voucher.jsonify());
        var reserva = reservaService.findByReservaId(voucher.getReservaId());
        var cliente = clienteService.findByClienteId(voucher.getClienteId());
        var comprobante = comprobanteService.findByComprobanteId(853);
        log.debug("Comprobante -> {}", comprobante.jsonify());
        var empresa = empresaService.findLast().get();
        var parametro = parametroService.findTop();
        var reservaContext = reservaContextService.findByReservaId(voucher.getReservaId());
        log.debug("ReservaContext -> {}", reservaContext.jsonify());
        if (dryRun == false) {
            var track = trackService.startTracking("transaccion-factura-programa-dia");
            log.debug("Track -> {}", track.jsonify());
            facturacionService.registraTransaccionFacturaProgramaDia(
                    reserva,
                    facturacionDto,
                    comprobante,
                    empresa,
                    cliente,
                    parametro,
                    reservaContext,
                    track,
                    soloFactura
            );
        }
    }

}
