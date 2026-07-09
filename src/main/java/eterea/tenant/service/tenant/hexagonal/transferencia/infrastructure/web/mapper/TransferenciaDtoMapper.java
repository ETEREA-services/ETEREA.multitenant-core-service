package eterea.tenant.service.tenant.hexagonal.transferencia.infrastructure.web.mapper;

import eterea.tenant.service.tenant.hexagonal.comprobante.application.service.ComprobanteService;
import eterea.tenant.service.tenant.hexagonal.comprobante.infrastructure.web.mapper.ComprobanteDtoMapper;
import eterea.tenant.service.tenant.hexagonal.transferencia.domain.model.Transferencia;
import eterea.tenant.service.tenant.hexagonal.transferencia.infrastructure.web.dto.TransferenciaResponse;
import eterea.tenant.service.tenant.model.dto.TransferenciaDto;
import org.springframework.stereotype.Component;

@Component
public class TransferenciaDtoMapper {

    private final ComprobanteService comprobanteService;
    private final ComprobanteDtoMapper comprobanteDtoMapper;

    public TransferenciaDtoMapper(ComprobanteService comprobanteService, ComprobanteDtoMapper comprobanteDtoMapper) {
        this.comprobanteService = comprobanteService;
        this.comprobanteDtoMapper = comprobanteDtoMapper;
    }

    public TransferenciaResponse toResponse(Transferencia domain) {
        if (domain == null) {
            return null;
        }
        return TransferenciaResponse.builder()
                .transferenciaId(domain.getTransferenciaId())
                .negocioIdDesde(domain.getNegocioIdDesde())
                .negocioIdHasta(domain.getNegocioIdHasta())
                .numeroTransferencia(domain.getNumeroTransferencia())
                .fecha(domain.getFecha())
                .total(domain.getTotal())
                .ordenContable(domain.getOrdenContable())
                .transferido(domain.getTransferido())
                .comprobanteId(domain.getComprobanteId())
                .fechaOtro(domain.getFechaOtro())
                .build();
    }

    public TransferenciaDto toTransferenciaDto(Transferencia domain) {
        if (domain == null) {
            return null;
        }
        return TransferenciaDto.builder()
                .transferenciaId(domain.getTransferenciaId())
                .negocioIdDesde(domain.getNegocioIdDesde())
                .negocioIdHasta(domain.getNegocioIdHasta())
                .numeroTransferencia(domain.getNumeroTransferencia())
                .fecha(domain.getFecha())
                .total(domain.getTotal())
                .ordenContable(domain.getOrdenContable())
                .transferido(domain.getTransferido())
                .comprobanteId(domain.getComprobanteId())
                .fechaOtro(domain.getFechaOtro())
                .comprobante(comprobanteDtoMapper.toComprobanteDto(comprobanteService.findByComprobanteId(domain.getComprobanteId())))
                .build();
    }
}
