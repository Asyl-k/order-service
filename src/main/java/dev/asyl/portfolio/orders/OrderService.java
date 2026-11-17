package dev.asyl.portfolio.orders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class OrderService {
    private final OrderRepository repository;
    public OrderService(OrderRepository repository) { this.repository = repository; }
    public CustomerOrder create(String idempotencyKey, String customerId) {
        if (idempotencyKey == null || idempotencyKey.isBlank()) throw new IllegalArgumentException("Idempotency-Key is required");
        return repository.findByIdempotencyKey(idempotencyKey)
                .orElseGet(() -> repository.save(new CustomerOrder(idempotencyKey, customerId)));
    }
    @Transactional(readOnly = true)
    public List<CustomerOrder> findAll() { return repository.findAll(); }
    public CustomerOrder transition(Long id, OrderStatus target) {
        CustomerOrder order = repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        order.transition(target);
        return repository.save(order);
    }
    public static class OrderNotFoundException extends RuntimeException {
        public OrderNotFoundException(Long id) { super("Order %d not found".formatted(id)); }
    }
}
