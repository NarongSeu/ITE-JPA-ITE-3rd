package co.istad.ite.features.excption;


import lombok.Builder;

@Builder
public record FieldErrorResponse(
        String field,
        String message
) {
}
