package eterea.tenant.service.modules.ventas.legacy.client.web;

import eterea.tenant.service.modules.ventas.legacy.OrderNote;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("import-service/orderNote")
public interface OrderNoteClient {

    @GetMapping("/{orderNumberId}")
    OrderNote findByOrderNumberId(@PathVariable Long orderNumberId);

    @GetMapping("/lastTwoDays")
    List<OrderNote> findAllCompletedByLastTwoDays();

}
