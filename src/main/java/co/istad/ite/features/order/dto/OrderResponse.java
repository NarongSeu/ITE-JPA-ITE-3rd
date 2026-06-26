    package co.istad.ite.features.order.dto;

    import java.time.LocalDate;
    import java.util.UUID;

    public record OrderResponse(
            UUID id,
            String customerId,
            String address,
            Float discount,
            String remark,
            Boolean status,
            LocalDate orderedAt,
            Boolean isDeleted
    ) {
    }
