package eterea.tenant.service.tenant.hexagonal.articulo.application.service;

import eterea.tenant.service.tenant.hexagonal.articulo.domain.model.Articulo;
import eterea.tenant.service.tenant.hexagonal.articulo.domain.ports.in.*;
import eterea.tenant.service.tenant.hexagonal.articulo.domain.ports.in.*;
import eterea.tenant.service.tenant.hexagonal.articulo.infrastructure.web.dto.TotalArticuloResponse;
import eterea.tenant.service.tenant.kotlin.model.VoucherProducto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArticuloService {

    private final CreateArticuloUseCase createArticuloUseCase;
    private final FindAllArticulosUseCase findAllArticulosUseCase;
    private final FindArticuloByArticuloIdUseCase findArticuloByArticuloIdUseCase;
    private final FindArticuloByAutoNumericoUseCase findArticuloByAutoNumericoUseCase;
    private final FindArticuloByMascaraBalanzaUseCase findArticuloByMascaraBalanzaUseCase;
    private final FindArticulosBySearchUseCase findArticulosBySearchUseCase;
    private final FindArticulosByVoucherUseCase findArticulosByVoucherUseCase;
    private final UpdateArticuloUseCase updateArticuloUseCase;
    private final CalculateTotalesByArticuloUseCase calculateTotalesByArticuloUseCase;

    public List<Articulo> findAll() {
        return findAllArticulosUseCase.findAll();
    }

    public List<Articulo> findAllBySearch(String chain) {
        return findArticulosBySearchUseCase.findBySearch(chain);
    }

    public List<Articulo> findAllByVoucher(List<VoucherProducto> voucherProductos) {
        return findArticulosByVoucherUseCase.findAllByVoucher(voucherProductos);
    }

    public Articulo findByArticuloId(String articuloId) {
        return findArticuloByArticuloIdUseCase.findByArticuloId(articuloId);
    }

    public Articulo findByAutoNumerico(Long autoNumerico) {
        return findArticuloByAutoNumericoUseCase.findByAutoNumerico(autoNumerico);
    }

    public Articulo findByMascaraBalanza(String mascaraBalanza) {
        return findArticuloByMascaraBalanzaUseCase.findByMascaraBalanza(mascaraBalanza);
    }

    public Articulo add(Articulo articulo) {
        return createArticuloUseCase.createArticulo(articulo);
    }

    public Articulo update(Articulo articulo, String articuloId) {
        return updateArticuloUseCase.updateArticulo(articuloId, articulo);
    }

    public TotalArticuloResponse calculateTotales(String articuloId, BigDecimal cantidad, BigDecimal precioUnitarioConIva) {
        return calculateTotalesByArticuloUseCase.calculateTotales(articuloId, cantidad, precioUnitarioConIva);
    }

}
