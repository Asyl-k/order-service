package dev.asyl.portfolio.orders;
import jakarta.validation.constraints.NotBlank;
public record CreateOrderRequest(@NotBlank String customerId) {}
