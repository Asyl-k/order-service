package dev.asyl.portfolio.orders;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "customer_orders", uniqueConstraints = @UniqueConstraint(columnNames = "idempotency_key"))
public class CustomerOrder {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "idempotency_key", nullable = false, updatable = false, length = 80)
    private String idempotencyKey;
    @Column(nullable = false, length = 100)
    private String customerId;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20)
    private OrderStatus status = OrderStatus.NEW;
    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    protected CustomerOrder() {}
    public CustomerOrder(String key, String customerId) { this.idempotencyKey = key; this.customerId = customerId; }
    public Long getId() { return id; }
    public String getIdempotencyKey() { return idempotencyKey; }
    public String getCustomerId() { return customerId; }
    public OrderStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public void transition(OrderStatus target) {
        boolean allowed = (status == OrderStatus.NEW && (target == OrderStatus.CONFIRMED || target == OrderStatus.CANCELLED))
                || (status == OrderStatus.CONFIRMED && target == OrderStatus.SHIPPED);
        if (!allowed) throw new IllegalStateException("Invalid order transition: %s -> %s".formatted(status, target));
        status = target;
    }
}
