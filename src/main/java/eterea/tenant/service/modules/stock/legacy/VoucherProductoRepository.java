package eterea.tenant.service.modules.stock.legacy;

import eterea.tenant.service.modules.stock.legacy.VoucherProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface VoucherProductoRepository extends JpaRepository<VoucherProducto, Long> {

    List<VoucherProducto> findAllByVoucherId(long voucherId);

    Optional<VoucherProducto> findByVoucherProductoId(long voucherProductoId);

    void deleteAllByVoucherId(long voucherId);

}
