package eterea.tenant.service.tenant.hexagonal.articulo.application.usecases;

import eterea.tenant.service.tenant.hexagonal.articulo.domain.model.Articulo;
import eterea.tenant.service.tenant.hexagonal.articulo.domain.ports.in.FindArticulosByVoucherUseCase;
import eterea.tenant.service.tenant.hexagonal.articulo.domain.ports.out.ArticuloRepository;
import eterea.tenant.service.tenant.kotlin.model.ProductoArticulo;
import eterea.tenant.service.tenant.kotlin.model.VoucherProducto;
import eterea.tenant.service.tenant.service.ProductoArticuloService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FindArticulosByVoucherUseCaseImpl implements FindArticulosByVoucherUseCase {

    private final ArticuloRepository articuloRepository;
    private final ProductoArticuloService productoArticuloService;

    @Override
    public List<Articulo> findAllByVoucher(List<VoucherProducto> voucherProductos) {
        List<Articulo> articulos = new ArrayList<>();
        for (VoucherProducto voucherProducto : voucherProductos) {
            List<ProductoArticulo> productoArticulos = productoArticuloService.findAllByProductoId(voucherProducto.getProductoId());
            for (ProductoArticulo productoArticulo : productoArticulos) {
                articuloRepository.findByArticuloId(productoArticulo.getArticuloId())
                        .ifPresent(articulos::add);
            }
        }
        return articulos;
    }
}