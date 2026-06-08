package co.istad.ite.dto;

import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UpdateCategoryRequest(
        @Size(max = 50)
        String name,
        @Size(max = 250)
        String description,
        String icon
) {
}
