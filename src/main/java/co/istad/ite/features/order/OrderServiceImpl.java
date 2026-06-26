package co.istad.ite.features.order;


import co.istad.ite.features.order.dto.*;
import co.istad.ite.features.product.Product;
import co.istad.ite.features.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    @Override
    public OrderResponse createNew(CreateOrderRequest createOrderRequest) {

        final Order order = orderMapper.mapOrderRequestToOrder(createOrderRequest);
        List<OrderLine> orderLines = new ArrayList<>();
        boolean isValidOrder = createOrderRequest.orderLines().stream()
                .allMatch(orderLineDto -> {
                    Optional<Product> productOptional = productRepository
                            .findByCode(orderLineDto.code());
                    if (productOptional.isPresent()) {
                        OrderLine orderLine = new OrderLine();
                        orderLine.setProduct(productOptional.get());
                        orderLine.setQty(orderLineDto.qty());
                        orderLine.setUnitPrice(orderLineDto.unitPrice());
                        orderLine.setOrder(order);
                        orderLines.add(orderLine);
                        return true;
                    }
                    return false;
                });
        if (!isValidOrder) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid order code");
        }

        order.setCustomerId("OrderProductList");
        order.setOrderLines(orderLines);
        order.setIsDelete(false);
        order.setStatus(false);
        order.setOrderedAt(LocalDateTime.now());
        Order saveOrder =  orderRepository.save(order);
        return orderMapper.mapOrderToOrderResponse(saveOrder);
    }

    @Override
    public PageResponse<OrderResponse> findAll(Integer pageNumber, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by("orderedAt").descending());

        Page<Order> orderPage = orderRepository.findAll(pageable);

        List<OrderResponse> orderResponses = orderPage.getContent().stream()
                .map(orderMapper::mapOrderToOrderResponse)
                .toList();

        return new PageResponse<>(
                orderResponses,
                orderPage.getNumber() + 1,
                orderPage.getSize(),
                orderPage.getTotalElements(),
                orderPage.getTotalPages(),
                orderPage.isLast()
        );
    }

    @Override
    public OrderResponse findByUuid(UUID uuid) {
        return orderRepository.findById(uuid)
                .map(orderMapper::mapOrderToOrderResponse)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Order has not been found."));
    }

    @Override
    public void softDelete(UUID uuid, SoftDeleteOrderRequest softDeleteOrderRequest) {
        Order order = orderRepository.findById(uuid)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Order has not been found."));

        order.setIsDelete(softDeleteOrderRequest.isDeleted());
        orderRepository.save(order);
        log.info("Order soft delete status updated to {} for ID: {}",
                softDeleteOrderRequest.isDeleted(), uuid);
    }

    @Override
    public void hardDelete(UUID uuid) {
        Order order = orderRepository.findById(uuid)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Order has not been found."));

        orderRepository.delete(order);
        log.info("Order hard deleted: {}", uuid);
    }

    @Override
    public void changePaymentStatus(UUID uuid, PaymentStatusRequest paymentStatusRequest) {
        Order order = orderRepository.findById(uuid)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Order has not been found."));

        order.setStatus(paymentStatusRequest.status());
        orderRepository.save(order);
        log.info("Payment status updated to {} for order: {}",
                paymentStatusRequest.status(), uuid);
    }


}
