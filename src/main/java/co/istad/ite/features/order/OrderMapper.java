package co.istad.ite.features.order;

import co.istad.ite.features.order.dto.CreateOrderRequest;
import co.istad.ite.features.order.dto.OrderResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
      OrderResponse mapOrderToOrderResponse(Order order);
      Order mapOrderRequestToOrder(CreateOrderRequest createOrderRequest);
}
