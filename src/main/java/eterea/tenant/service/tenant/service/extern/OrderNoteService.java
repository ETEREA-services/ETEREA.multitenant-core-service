package eterea.tenant.service.tenant.service.extern;

import eterea.tenant.service.tenant.client.web.OrderNoteClient;
import eterea.tenant.service.tenant.kotlin.extern.OrderNote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@Slf4j
public class OrderNoteService {

    private final OrderNoteClient orderNoteClient;

    public OrderNoteService(OrderNoteClient orderNoteClient) {
        this.orderNoteClient = orderNoteClient;
    }

    public OrderNote findByOrderNumberId(@PathVariable Long orderNumberId) {
        log.debug("Processing findByOrderNumberId");
        return orderNoteClient.findByOrderNumberId(orderNumberId);
    }

    public List<OrderNote> findAllCompletedByLastTwoDays() {
        return orderNoteClient.findAllCompletedByLastTwoDays();
    }

}
