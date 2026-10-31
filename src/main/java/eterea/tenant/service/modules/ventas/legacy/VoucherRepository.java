package eterea.tenant.service.modules.ventas.legacy;

import eterea.tenant.service.modules.ventas.legacy.Voucher;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.OffsetDateTime;
import java.util.*;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {

    List<Voucher> findAllByFechaVencimientoAndUsuario(OffsetDateTime fechaVencimiento, String usuario);

    List<Voucher> findAllByFechaServicio(OffsetDateTime fechaServicio, Sort sort);

    Optional<Voucher> findByReservaId(Long reservaId);

    Optional<Voucher> findByVoucherId(Long voucherId);

    Optional<Voucher> findTopByNumeroVoucherContains(String numeroVoucher);

    Optional<Voucher> findTopByNumeroVoucherContainsAndFechaTomaAfter(String numeroVoucher, OffsetDateTime fechaToma);

}
