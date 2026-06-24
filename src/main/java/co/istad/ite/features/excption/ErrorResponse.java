package co.istad.ite.features.excption;


import lombok.Builder;

@Builder
public record ErrorResponse<T>(
        Boolean status,
        Integer code,
        String message,
        T errors
) {
}
