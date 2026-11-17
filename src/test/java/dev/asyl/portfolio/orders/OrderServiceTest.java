package dev.asyl.portfolio.orders;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock OrderRepository repository;
    @InjectMocks OrderService service;
    @Test void idempotentRetryReturnsExistingOrder() {
        var existing = new CustomerOrder("request-1", "customer-1");
        when(repository.findByIdempotencyKey("request-1")).thenReturn(Optional.of(existing));
        assertSame(existing, service.create("request-1", "customer-1"));
        verify(repository, never()).save(any());
    }
    @Test void orderStateMachineRejectsShippingBeforeConfirmation() {
        var order = new CustomerOrder("request-2", "customer-1");
        assertThrows(IllegalStateException.class, () -> order.transition(OrderStatus.SHIPPED));
        order.transition(OrderStatus.CONFIRMED);
        order.transition(OrderStatus.SHIPPED);
        assertEquals(OrderStatus.SHIPPED, order.getStatus());
    }
    @Test void idempotencyKeyIsMandatory() {
        assertThrows(IllegalArgumentException.class, () -> service.create(" ", "customer-1"));
    }
}
