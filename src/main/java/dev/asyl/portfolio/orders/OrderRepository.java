package dev.asyl.portfolio.orders;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {
    Optional<CustomerOrder> findByIdempotencyKey(String idempotencyKey);
}
