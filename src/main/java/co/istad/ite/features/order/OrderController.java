package co.istad.ite.features.order;


import co.istad.ite.features.order.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // Create new order
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderResponse createOrder(@Valid @RequestBody CreateOrderRequest createOrderRequest) {
        return orderService.createNew(createOrderRequest);
    }

    // Find all orders with pagination and sort DESC
    @GetMapping
    public PageResponse<OrderResponse> findAllOrders(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        return orderService.findAll(pageNumber, pageSize);
    }

    // Soft delete order by ID
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}/soft-delete")
    public void softDeleteOrder(
            @PathVariable UUID id,
            @Valid @RequestBody SoftDeleteOrderRequest softDeleteOrderRequest) {

        orderService.softDelete(id, softDeleteOrderRequest);
    }

    // Hard delete order by ID
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void hardDeleteOrder(@PathVariable UUID id) {
        orderService.hardDelete(id);
    }

    // Set payment status by ID
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}/status")
    public void updatePaymentStatus(
            @PathVariable UUID id,
            @Valid @RequestBody PaymentStatusRequest paymentStatusRequest) {

        orderService.changePaymentStatus(id, paymentStatusRequest);
    }

}
