package eterea.tenant.service.modules.ventas.legacy.service.facade;

import eterea.tenant.service.modules.ventas.legacy.Voucher;
import eterea.tenant.service.modules.platform.legacy.hexagonal.empresa.application.service.EmpresaService;
import eterea.tenant.service.modules.platform.negocio.application.service.NegocioService;
import eterea.tenant.service.modules.platform.negocio.domain.model.Negocio;
import eterea.tenant.service.modules.ventas.legacy.VoucherException;
import eterea.tenant.service.modules.ventas.legacy.OrderNote;
import eterea.tenant.service.modules.ventas.legacy.Product;
import eterea.tenant.service.modules.ventas.legacy.model.dto.ProgramaDiaDto;
import eterea.tenant.service.modules.platform.legacy.model.Track;
import eterea.tenant.service.modules.ventas.legacy.service.VoucherService;
import eterea.tenant.service.modules.ventas.legacy.service.extern.OrderNoteService;
import eterea.tenant.service.modules.ventas.legacy.service.facade.reserva.ProductsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class VouchersService {

    private final NegocioService negocioService;
    private final OrderNoteService orderNoteService;
    private final VoucherService voucherService;
    private final ProductsService productsService;
    private final EmpresaService empresaService;

    public ProgramaDiaDto importOneFromWeb(Long orderNumberId, Track track) {
        log.debug("\n\nProcessing importOneFromWeb\n\n");
        OrderNote orderNote = getOrderNoteById(orderNumberId);
        if (orderNote == null || !isOrderCompleted(orderNote)) {
            return createErrorResponse("Error: Order Note pendiente de PAGO");
        }
        log.debug("\n\nOrderNote -> {}\n\n", orderNote.jsonify());

        if (isVoucherAlreadyRegistered(orderNumberId)) {
            return createErrorResponse("Error: Programa por el Día YA registrado");
        }

        if (Objects.requireNonNull(orderNote.getProducts()).isEmpty()) {
            return createErrorResponse("Error: reserva sin productos");
        }

        if (orderNote.getProducts().size() > 1) {
            return createErrorResponse("Error: más de un producto en la reserva");
        }

        Product product = orderNote.getProducts().getFirst();
        assert product != null;
        return processProduct(orderNote, product, negocioService.getNegocioById(empresaService.findLast().get().getNegocioId()), track);
    }

    private OrderNote getOrderNoteById(Long orderNumberId) {
        log.debug("Processing getOrderNoteById");
        return orderNoteService.findByOrderNumberId(orderNumberId);
    }

    private boolean isOrderCompleted(OrderNote orderNote) {
        log.debug("Processing isOrderCompleted");
        var payment = orderNote.getPayment();
        if (payment == null) {
            return false;
        }
        if (payment.getEstado().equals("EXPIRADA")) {
            return false;
        }
        return Arrays.asList("Completado", "Completed").contains(orderNote.getOrderStatus());
    }

    private boolean isVoucherAlreadyRegistered(Long orderNumberId) {
        log.debug("Processing isVoucherAlreadyRegistered");
        try {
            voucherService.findByNumeroVoucherAlreadyRegistered(String.valueOf(orderNumberId));
            return true;
        } catch (VoucherException e) {
            log.debug("Voucher not found, proceeding.");
            return false;
        }
    }

    private ProgramaDiaDto createErrorResponse(String message) {
        log.debug("Processing createErrorResponse");
        return ProgramaDiaDto.builder()
                .errorMessage(message)
                .build();
    }

    private ProgramaDiaDto processProduct(OrderNote orderNote, Product product, Negocio negocio, Track track) {
        log.debug("\n\nProcessing VouchersService.processProduct\n\n");
        switch (product.getSku()) {
            case "parque_termal_cacheuta":
            case "tarde_termaspa":
                return productsService.processOneProduct(orderNote, 130, 475, product, negocio, track);
            case "termaspa_fullday":
                if (product.getServiciosAdicionales().isEmpty()) {
                    return productsService.processOneProduct(orderNote, 130, 475, product, negocio, track);
                }
                break;
            case "parque_termal_traslado":
                if (product.getServiciosAdicionales().isEmpty()) {
                    return productsService.processOneProduct(orderNote, 31, 475, product, negocio, track);
                }
                break;
            case null:
                break;
            default:
                break;
        }
        return createErrorResponse("Error: no corresponde a un producto facturable");
    }

}
