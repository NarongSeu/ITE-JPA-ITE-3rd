package co.istad.ite.features.order;

import co.istad.ite.features.order.dto.*;

import java.util.UUID;

public interface OrderService {

    OrderResponse createNew(CreateOrderRequest createOrderRequest);

    PageResponse<OrderResponse> findAll(Integer pageNumber, Integer pageSize);

    OrderResponse findByUuid(UUID uuid);

    void softDelete(UUID uuid, SoftDeleteOrderRequest softDeleteOrderRequest);

    void hardDelete(UUID uuid);

    void changePaymentStatus(UUID uuid, PaymentStatusRequest paymentStatusRequest);
}
