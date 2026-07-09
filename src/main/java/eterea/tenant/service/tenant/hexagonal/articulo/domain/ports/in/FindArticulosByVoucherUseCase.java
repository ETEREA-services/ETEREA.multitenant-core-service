package eterea.tenant.service.tenant.hexagonal.articulo.domain.ports.in;

import eterea.tenant.service.tenant.hexagonal.articulo.domain.model.Articulo;
import eterea.tenant.service.tenant.kotlin.model.VoucherProducto;
import java.util.List;

public interface FindArticulosByVoucherUseCase {
    List<Articulo> findAllByVoucher(List<VoucherProducto> voucherProductos);
}
