package dev.asyl.portfolio.orders;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService service;
    public OrderController(OrderService service) { this.service = service; }
    @GetMapping public List<CustomerOrder> findAll() { return service.findAll(); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public CustomerOrder create(@RequestHeader("Idempotency-Key") String key, @Valid @RequestBody CreateOrderRequest body) {
        return service.create(key, body.customerId());
    }
    @PatchMapping("/{id}/status")
    public CustomerOrder transition(@PathVariable Long id, @RequestParam OrderStatus to) { return service.transition(id, to); }
}
