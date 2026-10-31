package eterea.tenant.service.modules.stock.articulo.domain.ports.in;

import eterea.tenant.service.modules.stock.articulo.domain.model.Articulo;
import eterea.tenant.service.modules.stock.legacy.VoucherProducto;
import java.util.List;

public interface FindArticulosByVoucherUseCase {
    List<Articulo> findAllByVoucher(List<VoucherProducto> voucherProductos);
}
